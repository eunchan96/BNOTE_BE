package com.bnote.domain.bible.controller;

import com.bnote.domain.bible.dto.response.BibleChapterResponse;
import com.bnote.domain.bible.dto.response.BibleSearchResponse;
import com.bnote.domain.bible.dto.response.TranslationResponse;
import com.bnote.global.response.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 성경 관련 REST API
 * - 인증 없이 조회 가능 (SecurityConfig permitAll)
 */
@Tag(name = "Bible", description = "성경 열람/검색/대역본 관련 API")
@RequestMapping("api/v1")
public interface BibleControllerDocs {

	@Operation(summary = "성경 장 조회", description = "책(bookId, 1~66)과 장을 지정해 해당 장의 절 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 bookId 또는 지원하지 않는 번역본",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "해당 장을 찾을 수 없음",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<BibleChapterResponse> getChapter(
		@Parameter(description = "성경 권 번호 (1=창세기 ~ 66=요한계시록)") Integer bookId,
		@Parameter(description = "장") Integer chapter,
		@Parameter(description = "번역본 코드 (미지정 시 NKRV)") String translation
	);

	@Operation(summary = "성경 통합 검색", description = "본문에 검색어(2글자 이상)가 포함된 절을 찾습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "검색 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "400", description = "검색어가 너무 짧거나 지원하지 않는 번역본",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<BibleSearchResponse> search(
		@Parameter(description = "검색어 (2글자 이상)") String keyword,
		@Parameter(description = "번역본 코드 (미지정 시 NKRV)") String translation
	);

	@Operation(summary = "대역본 목록 조회", description = "지원하는 성경 번역본 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<TranslationResponse> getTranslations();
}