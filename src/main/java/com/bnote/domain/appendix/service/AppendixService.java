package com.bnote.domain.appendix.service;

import com.bnote.domain.appendix.dto.response.*;
import com.bnote.domain.appendix.exception.AppendixException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 주기도문/사도신경/십계명/교독문은 회원과 무관한 정적 콘텐츠라 DB 테이블 없이,
 * 서버 기동 시 Android 앱과 동일한 4개 JSON 파일(appendix-data/*.json)을 읽어 메모리에 캐싱한다.
 */
@Slf4j
@Service
public class AppendixService {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String basePath;

	private VersionedTextResponse lordsPrayer;
	private VersionedTextResponse apostlesCreed;
	private TenCommandmentsResponse tenCommandments;
	private List<ResponsiveReadingResponse> responsiveReadings = List.of();

	public AppendixService(@Value("${appendix.data.path:appendix-data/}") String basePath) {
		this.basePath = basePath;
	}

	@PostConstruct
	void load() {
		lordsPrayer = loadVersionedText("lords_prayer.json");
		apostlesCreed = loadVersionedText("apostles_creed.json");
		tenCommandments = loadTenCommandments("ten_commandments.json");
		responsiveReadings = loadResponsiveReadings("responsive_readings.json");
	}

	public VersionedTextResponse getLordsPrayer() {
		if (lordsPrayer == null) {
			throw AppendixException.lordsPrayerNotFound();
		}
		return lordsPrayer;
	}

	public VersionedTextResponse getApostlesCreed() {
		if (apostlesCreed == null) {
			throw AppendixException.apostlesCreedNotFound();
		}
		return apostlesCreed;
	}

	public TenCommandmentsResponse getTenCommandments() {
		if (tenCommandments == null) {
			throw AppendixException.tenCommandmentsNotFound();
		}
		return tenCommandments;
	}

	public List<ResponsiveReadingResponse> getResponsiveReadings() {
		return responsiveReadings;
	}

	public ResponsiveReadingResponse getResponsiveReading(int number) {
		return responsiveReadings.stream()
				.filter(r -> r.number() == number)
				.findFirst()
				.orElseThrow(() -> AppendixException.responsiveReadingNotFound(number));
	}

	private VersionedTextResponse loadVersionedText(String fileName) {
		JsonNode root = readJson(fileName);
		if (root == null) {
			return null;
		}

		List<TextVersionResponse> versions = new ArrayList<>();
		for (JsonNode v : root.get("versions")) {
			versions.add(new TextVersionResponse(text(v, "id"), text(v, "label"), stringList(v.get("lines"))));
		}
		return new VersionedTextResponse(text(root, "title"), versions);
	}

	private TenCommandmentsResponse loadTenCommandments(String fileName) {
		JsonNode root = readJson(fileName);
		if (root == null) {
			return null;
		}

		List<CommandmentItemResponse> commandments = new ArrayList<>();
		for (JsonNode c : root.get("commandments")) {
			commandments.add(new CommandmentItemResponse(c.get("number").asInt(), text(c, "text")));
		}

		JsonNode summaryNode = root.get("summary");
		CommandmentSummaryResponse summary = new CommandmentSummaryResponse(
				text(summaryNode, "text"), text(summaryNode, "reference")
		);

		return new TenCommandmentsResponse(
				text(root, "title"), stringList(root.get("intro")), commandments, text(root, "reference"), summary
		);
	}

	private List<ResponsiveReadingResponse> loadResponsiveReadings(String fileName) {
		JsonNode array = readJson(fileName);
		if (array == null) {
			return List.of();
		}

		List<ResponsiveReadingResponse> readings = new ArrayList<>();
		for (JsonNode r : array) {
			List<ResponsiveReadingLineResponse> lines = new ArrayList<>();
			for (JsonNode l : r.get("lines")) {
				lines.add(new ResponsiveReadingLineResponse(text(l, "speaker"), text(l, "text")));
			}
			readings.add(new ResponsiveReadingResponse(r.get("number").asInt(), text(r, "title"), lines));
		}
		return readings;
	}

	private JsonNode readJson(String fileName) {
		Resource resource = new ClassPathResource(basePath + fileName);
		if (!resource.exists()) {
			log.warn("[AppendixService] 부록 데이터 파일이 없어 건너뜁니다: {}{}", basePath, fileName);
			return null;
		}
		try (InputStream in = resource.getInputStream()) {
			return objectMapper.readTree(in);
		} catch (IOException e) {
			log.error("[AppendixService] {} 파일을 읽는 중 오류가 발생했습니다.", fileName, e);
			return null;
		}
	}

	private String text(JsonNode obj, String field) {
		JsonNode node = obj.get(field);
		return (node == null || node.isNull()) ? null : node.asString();
	}

	private List<String> stringList(JsonNode array) {
		List<String> result = new ArrayList<>();
		if (array == null) {
			return result;
		}
		for (JsonNode n : array) {
			result.add(n.asString());
		}
		return result;
	}
}