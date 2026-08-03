package com.bnote.domain.bible.memo.controller;

import com.bnote.domain.bible.memo.dto.request.VerseMemoRequest;
import com.bnote.domain.bible.memo.dto.request.VerseMemoUpdateRequest;
import com.bnote.domain.bible.memo.dto.response.VerseMemoResponse;
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

@Tag(name = "VerseMemo", description = "구절 메모 API (한 위치에 여러 개 작성 가능)")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/verse-memos")
public interface VerseMemoControllerDocs {

	@Operation(summary = "구절 메모 목록 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<VerseMemoResponse>> getByChapter(
		@Parameter(description = "성경 권 번호") Integer bookId,
		@Parameter(description = "장") Integer chapter
	);

	@Operation(summary = "구절 메모 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<VerseMemoResponse> create(@Valid @RequestBody VerseMemoRequest request);

	@Operation(summary = "구절 메모 수정")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "수정 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 메모",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<VerseMemoResponse> update(
		@Parameter(description = "구절 메모 id") Long id,
		@Valid @RequestBody VerseMemoUpdateRequest request
	);

	@Operation(summary = "구절 메모 삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 메모",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> delete(@Parameter(description = "구절 메모 id") Long id);
}