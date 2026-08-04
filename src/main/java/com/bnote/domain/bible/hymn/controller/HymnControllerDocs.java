package com.bnote.domain.bible.hymn.controller;

import com.bnote.domain.bible.hymn.dto.response.HymnCategoryResponse;
import com.bnote.domain.bible.hymn.dto.response.HymnResponse;
import com.bnote.global.response.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Hymn", description = "찬송가 API (인증 불필요)")
public interface HymnControllerDocs {

	@Operation(summary = "찬송가 목록 조회", description = "categoryId 또는 keyword로 필터링할 수 있습니다. 둘 다 없으면 전체 645곡을 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<HymnResponse>> getAll(
		@Parameter(description = "소분류 카테고리 id") Long categoryId,
		@Parameter(description = "제목/장번호 검색어") String keyword
	);

	@Operation(summary = "찬송가 상세 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 장번호",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<HymnResponse> getByNumber(@Parameter(description = "찬송가 장번호 (1~645)") Integer number);

	@Operation(summary = "대분류 카테고리 목록 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<HymnCategoryResponse>> getMajorCategories();

	@Operation(summary = "소분류 카테고리 목록 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<HymnCategoryResponse>> getMinorCategories(@Parameter(description = "대분류 카테고리 id") Long majorId);
}