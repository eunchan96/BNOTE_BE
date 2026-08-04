package com.bnote.domain.mypage.verseofyear.controller;

import com.bnote.domain.mypage.verseofyear.dto.request.VerseOfYearRequest;
import com.bnote.domain.mypage.verseofyear.dto.response.VerseOfYearResponse;
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

@Tag(name = "VerseOfYear", description = "올해의 말씀 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/verse-of-year")
public interface VerseOfYearControllerDocs {

	@Operation(summary = "올해의 말씀 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "등록된 올해의 말씀 없음",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<VerseOfYearResponse> getByYear(@Parameter(description = "연도") Integer year);

	@Operation(summary = "올해의 말씀 등록/수정", description = "같은 연도로 다시 등록하면 기존 내용을 덮어씁니다(upsert).")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "등록/수정 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<VerseOfYearResponse> save(@Valid @RequestBody VerseOfYearRequest request);
}