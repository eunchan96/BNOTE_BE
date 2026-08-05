package com.bnote.domain.appendix.seed;

import com.bnote.domain.appendix.entity.AppendixText;
import com.bnote.domain.appendix.entity.ResponsiveReadingEntity;
import com.bnote.domain.appendix.repository.AppendixTextRepository;
import com.bnote.domain.appendix.repository.ResponsiveReadingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * 앱 시작 시 부록이 비어있으면 appendix-data/*.json을 읽어 DB에 채운다.
 * 예전엔 서버 기동 시 파일을 메모리에 올려두고 서빙했는데, 그러면 Render처럼
 * 원본 JSON이 없는(gitignore돼서 배포에 안 올라가는) 환경에서는 항상 비어있게 된다.
 * 다른 시더들과 동일하게 "로컬에서 한 번 Supabase로 시딩 → 배포 서버는 DB만 읽음" 구조로 통일한다.
 */
@Slf4j
@Component
public class AppendixSeeder implements ApplicationRunner {

	private final AppendixTextRepository appendixTextRepository;
	private final ResponsiveReadingRepository responsiveReadingRepository;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String basePath;
	private final boolean seedOnStartup;
	private final AppendixSeeder self;

	public AppendixSeeder(
		AppendixTextRepository appendixTextRepository,
		ResponsiveReadingRepository responsiveReadingRepository,
		@Value("${appendix.seed.path:appendix-data/}") String basePath,
		@Value("${appendix.seed.on-startup:true}") boolean seedOnStartup,
		@Lazy AppendixSeeder self
	) {
		this.appendixTextRepository = appendixTextRepository;
		this.responsiveReadingRepository = responsiveReadingRepository;
		this.basePath = basePath;
		this.seedOnStartup = seedOnStartup;
		this.self = self;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (!seedOnStartup) {
			log.info("[AppendixSeeder] appendix.seed.on-startup=false 라서 자동 시딩을 건너뜁니다.");
			return;
		}
		self.seedIfEmpty();
	}

	@Transactional
	public void seedIfEmpty() {
		seedText("lords-prayer", "lords_prayer.json");
		seedText("apostles-creed", "apostles_creed.json");
		seedText("ten-commandments", "ten_commandments.json");
		seedResponsiveReadings("responsive_readings.json");
	}

	private void seedText(String id, String fileName) {
		if (appendixTextRepository.existsById(id)) {
			return;
		}
		JsonNode root = readJson(fileName);
		if (root == null) {
			return;
		}

		String title = root.get("title") == null ? null : root.get("title").asString();
		appendixTextRepository.save(
			AppendixText.builder().id(id).title(title).contentJson(root.toString()).build()
		);
		log.info("[AppendixSeeder] {} 시딩 완료", id);
	}

	private void seedResponsiveReadings(String fileName) {
		if (responsiveReadingRepository.count() > 0) {
			return;
		}
		JsonNode array = readJson(fileName);
		if (array == null) {
			return;
		}

		int count = 0;
		for (JsonNode r : array) {
			int number = r.get("number").asInt();
			String title = r.get("title") == null ? null : r.get("title").asString();
			JsonNode lines = r.get("lines");
			responsiveReadingRepository.save(
				ResponsiveReadingEntity.builder()
					.number(number)
					.title(title)
					.linesJson(lines == null ? "[]" : lines.toString())
					.build()
			);
			count++;
		}
		log.info("[AppendixSeeder] 교독문 {}편 시딩 완료", count);
	}

	private JsonNode readJson(String fileName) {
		Resource resource = new ClassPathResource(basePath + fileName);
		if (!resource.exists()) {
			log.warn("[AppendixSeeder] 시드 파일이 없어 건너뜁니다: {}{}", basePath, fileName);
			return null;
		}
		try (InputStream in = resource.getInputStream()) {
			return objectMapper.readTree(in);
		} catch (IOException e) {
			log.error("[AppendixSeeder] {} 파일을 읽는 중 오류가 발생했습니다.", fileName, e);
			return null;
		}
	}
}