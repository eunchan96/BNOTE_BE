package com.bnote.domain.bible.scrap.controller;

import com.bnote.domain.bible.scrap.dto.request.ScrapRequest;
import com.bnote.domain.bible.scrap.dto.response.ScrapResponse;
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

@Tag(name = "Scrap", description = "스크랩 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/scraps")
public interface ScrapControllerDocs {

	@Operation(summary = "스크랩 목록 조회 (그룹별)")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 그룹",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<ScrapResponse>> getByGroup(@Parameter(description = "스크랩 그룹 id") Long groupId);

	@Operation(summary = "스크랩 등록", description = "등록 시점의 본문을 스냅샷으로 함께 저장합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아닌 그룹",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 그룹",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<ScrapResponse> create(@Valid @RequestBody ScrapRequest request);

	@Operation(summary = "스크랩 삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 스크랩",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> delete(@Parameter(description = "스크랩 id") Long id);
}