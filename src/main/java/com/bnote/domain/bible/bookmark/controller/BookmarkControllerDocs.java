package com.bnote.domain.bible.bookmark.controller;

import com.bnote.domain.bible.bookmark.dto.request.BookmarkToggleRequest;
import com.bnote.domain.bible.bookmark.dto.response.BookmarkResponse;
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

@Tag(name = "Bookmark", description = "북마크 · 절 전체 하이라이트 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/bookmarks")
public interface BookmarkControllerDocs {

	@Operation(summary = "북마크 · 하이라이트 목록 조회", description = "특정 장에서 북마크되었거나 하이라이트된 절 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 bookId",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<BookmarkResponse>> getByChapter(
		@Parameter(description = "성경 권 번호") Integer bookId,
		@Parameter(description = "장") Integer chapter
	);

	@Operation(
		summary = "북마크 · 하이라이트 토글",
		description = "지정한 절의 북마크/하이라이트 여부를 갱신합니다. 요청에 담긴 필드만 반영되고, 둘 다 false가 되면 레코드가 삭제됩니다."
	)
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "토글 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 bookId",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<BookmarkResponse> toggle(
		Integer bookId, Integer chapter, Integer verse,
		@Valid @RequestBody BookmarkToggleRequest request
	);
}