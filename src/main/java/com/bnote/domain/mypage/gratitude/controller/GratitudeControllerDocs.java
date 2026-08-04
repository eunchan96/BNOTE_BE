package com.bnote.domain.mypage.gratitude.controller;

import com.bnote.domain.mypage.gratitude.dto.request.GratitudeNoteRequest;
import com.bnote.domain.mypage.gratitude.dto.response.GratitudeNoteResponse;
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

@Tag(name = "Gratitude", description = "감사노트 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/gratitude-notes")
public interface GratitudeControllerDocs {

	@Operation(summary = "감사노트 목록 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<GratitudeNoteResponse>> getAll();

	@Operation(summary = "감사노트 등록/수정", description = "같은 날짜로 다시 등록하면 기존 내용을 덮어씁니다(upsert).")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "등록/수정 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<GratitudeNoteResponse> save(@Valid @RequestBody GratitudeNoteRequest request);

	@Operation(summary = "감사노트 삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 감사노트",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> delete(@Parameter(description = "감사노트 id") Long id);
}