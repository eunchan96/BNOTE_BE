package com.bnote.domain.application.controller;

import com.bnote.domain.application.dto.request.ApplicationRequest;
import com.bnote.domain.application.dto.response.ApplicationResponse;
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

@Tag(name = "Application", description = "적용(묵상/기도/순종) API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/applications")
public interface ApplicationControllerDocs {

	@Operation(summary = "적용 목록 조회", description = "keyword로 제목을 검색할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<ApplicationResponse>> getAll(@Parameter(description = "제목 검색어") String keyword);

	@Operation(summary = "적용 상세 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 적용",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<ApplicationResponse> getById(@Parameter(description = "적용 id") Long id);

	@Operation(summary = "적용 등록", description = "본문(bibleRefs), 연결할 설교노트(sermonIds)는 여러 개 등록할 수 있습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<ApplicationResponse> create(@Valid @RequestBody ApplicationRequest request);

	@Operation(summary = "적용 수정")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "수정 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 적용",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<ApplicationResponse> update(@Parameter(description = "적용 id") Long id, @Valid @RequestBody ApplicationRequest request);

	@Operation(summary = "적용 삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 적용",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> delete(@Parameter(description = "적용 id") Long id);
}