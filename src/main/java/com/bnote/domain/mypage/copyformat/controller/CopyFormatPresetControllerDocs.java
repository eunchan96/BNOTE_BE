package com.bnote.domain.mypage.copyformat.controller;

import com.bnote.domain.mypage.copyformat.dto.request.CopyFormatPresetRequest;
import com.bnote.domain.mypage.copyformat.dto.response.CopyFormatPresetResponse;
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

@Tag(name = "CopyFormatPreset", description = "텍스트 복사 형식 프리셋 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/copy-format-presets")
public interface CopyFormatPresetControllerDocs {

	@Operation(summary = "복사 형식 프리셋 목록 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<CopyFormatPresetResponse>> getAll();

	@Operation(summary = "복사 형식 프리셋 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<CopyFormatPresetResponse> create(@Valid @RequestBody CopyFormatPresetRequest request);

	@Operation(summary = "복사 형식 프리셋 삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 프리셋",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> delete(@Parameter(description = "프리셋 id") Long id);
}