package com.bnote.domain.appendix.controller;

import com.bnote.domain.appendix.dto.response.ResponsiveReadingResponse;
import com.bnote.domain.appendix.dto.response.TenCommandmentsResponse;
import com.bnote.domain.appendix.dto.response.VersionedTextResponse;
import com.bnote.global.response.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Appendix", description = "부록 API (인증 불필요, 정적 콘텐츠)")
public interface AppendixControllerDocs {

	@Operation(summary = "주기도문 조회", description = "여러 번역본(versions)을 함께 반환합니다.")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))))
	RsData<VersionedTextResponse> getLordsPrayer();

	@Operation(summary = "사도신경 조회", description = "여러 번역본(versions)을 함께 반환합니다.")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))))
	RsData<VersionedTextResponse> getApostlesCreed();

	@Operation(summary = "십계명 조회")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))))
	RsData<TenCommandmentsResponse> getTenCommandments();

	@Operation(summary = "교독문 전체 목록 조회")
	@ApiResponses(@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))))
	RsData<List<ResponsiveReadingResponse>> getResponsiveReadings();

	@Operation(summary = "교독문 상세 조회")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "조회 성공",
					content = @Content(schema = @Schema(implementation = RsData.class))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 번호",
					content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<ResponsiveReadingResponse> getResponsiveReading(@Parameter(description = "교독문 번호") Integer number);
}