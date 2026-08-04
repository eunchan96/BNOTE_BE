package com.bnote.domain.sermon.controller;

import com.bnote.domain.sermon.dto.request.PreacherRequest;
import com.bnote.domain.sermon.dto.response.PreacherResponse;
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

@Tag(name = "Preacher", description = "설교자 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/preachers")
public interface PreacherControllerDocs {

	@Operation(summary = "설교자 목록 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<PreacherResponse>> getAll();

	@Operation(summary = "설교자 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<PreacherResponse> create(@Valid @RequestBody PreacherRequest request);

	@Operation(summary = "설교자 수정")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "수정 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 설교자",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<PreacherResponse> update(@Parameter(description = "설교자 id") Long id, @Valid @RequestBody PreacherRequest request);

	@Operation(summary = "설교자 삭제")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 설교자",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> delete(@Parameter(description = "설교자 id") Long id);
}