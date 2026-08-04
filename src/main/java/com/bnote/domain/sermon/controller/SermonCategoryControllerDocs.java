package com.bnote.domain.sermon.controller;

import com.bnote.domain.sermon.dto.request.SermonCategoryRequest;
import com.bnote.domain.sermon.dto.response.SermonCategoryResponse;
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

@Tag(name = "SermonCategory", description = "설교 카테고리 API (최초 조회 시 기본 6종 자동 생성)")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/sermon-categories")
public interface SermonCategoryControllerDocs {

	@Operation(summary = "설교 카테고리 목록 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<SermonCategoryResponse>> getAll();

	@Operation(summary = "설교 카테고리 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<SermonCategoryResponse> create(@Valid @RequestBody SermonCategoryRequest request);

	@Operation(summary = "설교 카테고리 수정")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "수정 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 카테고리",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<SermonCategoryResponse> update(@Parameter(description = "카테고리 id") Long id, @Valid @RequestBody SermonCategoryRequest request);

	@Operation(summary = "설교 카테고리 삭제", description = "기본 제공 카테고리는 삭제할 수 없습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아니거나 기본 카테고리임",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 카테고리",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> delete(@Parameter(description = "카테고리 id") Long id);
}