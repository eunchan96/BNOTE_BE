package com.bnote.domain.mypage.memorization.controller;

import com.bnote.domain.mypage.memorization.dto.request.MemorizationReviewRequest;
import com.bnote.domain.mypage.memorization.dto.request.MemorizationVerseRequest;
import com.bnote.domain.mypage.memorization.dto.response.MemorizationVerseResponse;
import com.bnote.global.response.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "MemorizationVerse", description = "암송 구절 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/memorization-verses")
public interface MemorizationVerseControllerDocs {

	@Operation(summary = "암송 구절 목록 조회 (그룹별)")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 그룹",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<MemorizationVerseResponse>> getByGroup(@Parameter(description = "암송 그룹 id") Long groupId);

	@Operation(summary = "암송 구절 등록", description = "장을 넘어가지 않는 범위면 등록 시점 본문을 스냅샷으로 저장합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<MemorizationVerseResponse> create(@Valid @RequestBody MemorizationVerseRequest request);

	@Operation(summary = "암송 연습 기록", description = "연습을 마칠 때마다 호출합니다. reviewCount가 1씩 증가하고 mastered 여부가 갱신됩니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "기록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 구절",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<MemorizationVerseResponse> review(@Parameter(description = "암송 구절 id") Long id, @Valid @RequestBody MemorizationReviewRequest request);

	@Operation(summary = "암송 구절 삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 구절",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> delete(@Parameter(description = "암송 구절 id") Long id);
}