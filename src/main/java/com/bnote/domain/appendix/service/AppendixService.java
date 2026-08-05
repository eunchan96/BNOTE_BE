package com.bnote.domain.appendix.service;

import com.bnote.domain.appendix.dto.response.*;
import com.bnote.domain.appendix.entity.AppendixText;
import com.bnote.domain.appendix.exception.AppendixException;
import com.bnote.domain.appendix.repository.AppendixTextRepository;
import com.bnote.domain.appendix.repository.ResponsiveReadingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 주기도문/사도신경/십계명/교독문은 회원과 무관한 정적 콘텐츠지만, 이제는 DB(AppendixSeeder가 채움)에서 읽는다.
 * Render처럼 원본 JSON이 없는 배포 환경에서도, 시딩만 한 번 로컬에서 Supabase로 해두면
 * 배포 서버는 파일 없이도 DB로부터 정상 서빙할 수 있다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppendixService {

	private final AppendixTextRepository appendixTextRepository;
	private final ResponsiveReadingRepository responsiveReadingRepository;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public VersionedTextResponse getLordsPrayer() {
		return toVersionedText(findText("lords-prayer", AppendixException::lordsPrayerNotFound));
	}

	public VersionedTextResponse getApostlesCreed() {
		return toVersionedText(findText("apostles-creed", AppendixException::apostlesCreedNotFound));
	}

	public TenCommandmentsResponse getTenCommandments() {
		AppendixText entity = findText("ten-commandments", AppendixException::tenCommandmentsNotFound);
		JsonNode root = objectMapper.readTree(entity.getContentJson());

		List<CommandmentItemResponse> commandments = new ArrayList<>();
		for (JsonNode c : root.get("commandments")) {
			commandments.add(new CommandmentItemResponse(c.get("number").asInt(), text(c, "text")));
		}

		JsonNode summaryNode = root.get("summary");
		CommandmentSummaryResponse summary = new CommandmentSummaryResponse(
				text(summaryNode, "text"), text(summaryNode, "reference")
		);

		return new TenCommandmentsResponse(
				entity.getTitle(), stringList(root.get("intro")), commandments, text(root, "reference"), summary
		);
	}

	public List<ResponsiveReadingResponse> getResponsiveReadings() {
		return responsiveReadingRepository.findAllByOrderByNumberAsc().stream()
				.map(r -> new ResponsiveReadingResponse(r.getNumber(), r.getTitle(), parseLines(r.getLinesJson())))
				.toList();
	}

	public ResponsiveReadingResponse getResponsiveReading(int number) {
		var entity = responsiveReadingRepository.findById(number)
				.orElseThrow(() -> AppendixException.responsiveReadingNotFound(number));
		return new ResponsiveReadingResponse(entity.getNumber(), entity.getTitle(), parseLines(entity.getLinesJson()));
	}

	private AppendixText findText(String id, java.util.function.Supplier<AppendixException> notFound) {
		return appendixTextRepository.findById(id).orElseThrow(notFound);
	}

	private VersionedTextResponse toVersionedText(AppendixText entity) {
		JsonNode root = objectMapper.readTree(entity.getContentJson());
		List<TextVersionResponse> versions = new ArrayList<>();
		for (JsonNode v : root.get("versions")) {
			versions.add(new TextVersionResponse(text(v, "id"), text(v, "label"), stringList(v.get("lines"))));
		}
		return new VersionedTextResponse(entity.getTitle(), versions);
	}

	private List<ResponsiveReadingLineResponse> parseLines(String linesJson) {
		List<ResponsiveReadingLineResponse> lines = new ArrayList<>();
		for (JsonNode l : objectMapper.readTree(linesJson)) {
			lines.add(new ResponsiveReadingLineResponse(text(l, "speaker"), text(l, "text")));
		}
		return lines;
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