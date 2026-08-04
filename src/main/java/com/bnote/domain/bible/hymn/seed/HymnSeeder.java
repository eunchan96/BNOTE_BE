package com.bnote.domain.bible.hymn.seed;

import com.bnote.domain.bible.hymn.entity.Hymn;
import com.bnote.domain.bible.hymn.entity.HymnCategory;
import com.bnote.domain.bible.hymn.repository.HymnCategoryRepository;
import com.bnote.domain.bible.hymn.repository.HymnRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 앱 시작 시 찬송가가 비어있으면 hymn-data/hymns.json을 읽어 채운다.
 * JSON 안의 카테고리 id는 파일 안에서만 유효한 논리 id라, 대분류/소분류를 실제로 insert하면서
 * 새로 발급되는 DB id로 매핑해가며 hymn.categoryId를 치환한다(대/소분류가 둘 다 1부터 시작하는
 * id를 쓰다 보니 실제 앱에서 겪었던 충돌 버그를 여기서도 피하기 위함).
 */
@Slf4j
@Component
public class HymnSeeder implements ApplicationRunner {

	private final HymnRepository hymnRepository;
	private final HymnCategoryRepository hymnCategoryRepository;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String basePath;
	private final String fileName;
	private final boolean seedOnStartup;

	public HymnSeeder(
		HymnRepository hymnRepository,
		HymnCategoryRepository hymnCategoryRepository,
		@Value("${hymn.seed.path:hymn-data/}") String basePath,
		@Value("${hymn.seed.file-name:hymns.json}") String fileName,
		@Value("${hymn.seed.on-startup:true}") boolean seedOnStartup
	) {
		this.hymnRepository = hymnRepository;
		this.hymnCategoryRepository = hymnCategoryRepository;
		this.basePath = basePath;
		this.fileName = fileName;
		this.seedOnStartup = seedOnStartup;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (!seedOnStartup) {
			log.info("[HymnSeeder] hymn.seed.on-startup=false 라서 자동 시딩을 건너뜁니다.");
			return;
		}
		seedIfEmpty();
	}

	@Transactional
	public void seedIfEmpty() {
		if (hymnRepository.count() > 0) {
			return;
		}

		Resource resource = new ClassPathResource(basePath + fileName);
		if (!resource.exists()) {
			log.warn("[HymnSeeder] 시드 파일이 없어 건너뜁니다: {}{}", basePath, fileName);
			return;
		}

		JsonNode root;
		try (InputStream in = resource.getInputStream()) {
			root = objectMapper.readTree(in);
		} catch (IOException e) {
			log.error("[HymnSeeder] 시드 파일을 읽는 중 오류가 발생했습니다.", e);
			return;
		}

		Map<Long, Long> majorIdMap = seedMajorCategories(root.get("majorCategories"));
		Map<Long, Long> minorIdMap = seedMinorCategories(root.get("minorCategories"), majorIdMap);
		int hymnCount = seedHymns(root.get("hymns"), minorIdMap);

		log.info("[HymnSeeder] 대분류 {}개, 소분류 {}개, 찬송가 {}개 시딩 완료", majorIdMap.size(), minorIdMap.size(), hymnCount);
	}

	private Map<Long, Long> seedMajorCategories(JsonNode majorArray) {
		Map<Long, Long> majorIdMap = new HashMap<>();
		for (JsonNode obj : majorArray) {
			long jsonId = obj.get("id").asLong();
			HymnCategory saved = hymnCategoryRepository.save(
				HymnCategory.builder()
					.name(obj.get("name").asString())
					.parentId(null)
					.sortOrder(obj.get("sortOrder").asInt())
					.build()
			);
			majorIdMap.put(jsonId, saved.getId());
		}
		return majorIdMap;
	}

	private Map<Long, Long> seedMinorCategories(JsonNode minorArray, Map<Long, Long> majorIdMap) {
		Map<Long, Long> minorIdMap = new HashMap<>();
		for (JsonNode obj : minorArray) {
			long jsonId = obj.get("id").asLong();
			long jsonMajorId = obj.get("majorId").asLong();
			Long realMajorId = majorIdMap.get(jsonMajorId);
			if (realMajorId == null) {
				throw new IllegalStateException("알 수 없는 대분류 id 참조: " + jsonMajorId);
			}

			HymnCategory saved = hymnCategoryRepository.save(
				HymnCategory.builder()
					.name(obj.get("name").asString())
					.parentId(realMajorId)
					.sortOrder(obj.get("sortOrder").asInt())
					.build()
			);
			minorIdMap.put(jsonId, saved.getId());
		}
		return minorIdMap;
	}

	private int seedHymns(JsonNode hymnArray, Map<Long, Long> minorIdMap) {
		List<Hymn> hymns = new ArrayList<>(hymnArray.size());
		for (JsonNode obj : hymnArray) {
			long jsonCategoryId = obj.get("categoryId").asLong();
			Long realCategoryId = minorIdMap.get(jsonCategoryId);
			if (realCategoryId == null) {
				throw new IllegalStateException("알 수 없는 소분류 id 참조: " + jsonCategoryId);
			}

			hymns.add(
				Hymn.builder()
					.number(obj.get("number").asInt())
					.title(obj.get("title").asString())
					.categoryId(realCategoryId)
					.imageFileName(obj.get("image").asString())
					.youtubeSongUrl(obj.get("youtubeSong").asString())
					.youtubeMrUrl(obj.get("youtubeMr").asString())
					.build()
			);
		}
		hymnRepository.saveAll(hymns);
		return hymns.size();
	}
}