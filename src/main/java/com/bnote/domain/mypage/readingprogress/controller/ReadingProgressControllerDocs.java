package com.bnote.domain.mypage.readingprogress.controller;

import com.bnote.domain.mypage.readingprogress.dto.request.ReadingProgressRequest;
import com.bnote.domain.mypage.readingprogress.dto.response.ReadingProgressResponse;
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

@Tag(name = "ReadingProgress", description = "성경읽기표 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/reading-progress")
public interface ReadingProgressControllerDocs {

	@Operation(summary = "성경읽기표 목록 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<ReadingProgressResponse>> getAll();

	@Operation(summary = "성경읽기표 체크", description = "이미 체크된 장이면 기존 기록을 그대로 반환합니다(멱등).")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "체크 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 bookId",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<ReadingProgressResponse> check(@Valid @RequestBody ReadingProgressRequest request);

	@Operation(summary = "성경읽기표 체크 취소")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "취소 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "체크된 기록 없음",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> cancel(@Parameter(description = "성경읽기표 id") Long id);
}