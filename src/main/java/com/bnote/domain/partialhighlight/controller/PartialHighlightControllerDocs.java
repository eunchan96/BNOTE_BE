package com.bnote.domain.partialhighlight.controller;

import com.bnote.domain.partialhighlight.dto.request.PartialHighlightRequest;
import com.bnote.domain.partialhighlight.dto.response.PartialHighlightResponse;
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

@Tag(name = "PartialHighlight", description = "부분(드래그 선택) 하이라이트 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/partial-highlights")
public interface PartialHighlightControllerDocs {

	@Operation(summary = "부분 하이라이트 목록 조회", description = "장 전체 또는 특정 절의 부분 하이라이트를 조회합니다. verse를 생략하면 장 전체를 반환합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 bookId",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<PartialHighlightResponse>> getByLocation(
		@Parameter(description = "성경 권 번호") Integer bookId,
		@Parameter(description = "장") Integer chapter,
		@Parameter(description = "절 (생략 시 장 전체)") Integer verse
	);

	@Operation(summary = "부분 하이라이트 등록", description = "드래그로 선택한 범위에 부분 하이라이트를 등록합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<PartialHighlightResponse> create(@Valid @RequestBody PartialHighlightRequest request);

	@Operation(summary = "부분 하이라이트 삭제", description = "본인이 등록한 부분 하이라이트만 삭제할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 하이라이트",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> delete(@Parameter(description = "부분 하이라이트 id") Long id);
}