package com.bnote.domain.mypage.prayerrequest.controller;

import com.bnote.domain.mypage.prayerrequest.dto.request.PrayerRequestAnswerRequest;
import com.bnote.domain.mypage.prayerrequest.dto.request.PrayerRequestCreateRequest;
import com.bnote.domain.mypage.prayerrequest.dto.response.PrayerRequestResponse;
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

@Tag(name = "PrayerRequest", description = "기도제목 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/prayer-requests")
public interface PrayerRequestControllerDocs {

	@Operation(summary = "기도제목 목록 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<PrayerRequestResponse>> getAll();

	@Operation(summary = "기도제목 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<PrayerRequestResponse> create(@Valid @RequestBody PrayerRequestCreateRequest request);

	@Operation(summary = "기도제목 응답 체크")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "체크 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 기도제목",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<PrayerRequestResponse> markAnswered(@Parameter(description = "기도제목 id") Long id, @Valid @RequestBody PrayerRequestAnswerRequest request);

	@Operation(summary = "기도제목 삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 기도제목",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> delete(@Parameter(description = "기도제목 id") Long id);
}