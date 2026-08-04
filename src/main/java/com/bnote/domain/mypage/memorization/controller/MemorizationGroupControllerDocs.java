package com.bnote.domain.mypage.memorization.controller;

import com.bnote.domain.mypage.memorization.dto.request.MemorizationGroupRequest;
import com.bnote.domain.mypage.memorization.dto.response.MemorizationGroupResponse;
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

@Tag(name = "MemorizationGroup", description = "암송 그룹 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/memorization-groups")
public interface MemorizationGroupControllerDocs {

	@Operation(summary = "암송 그룹 목록 조회")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<List<MemorizationGroupResponse>> getAll();

	@Operation(summary = "암송 그룹 등록")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "등록 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<MemorizationGroupResponse> create(@Valid @RequestBody MemorizationGroupRequest request);

	@Operation(summary = "암송 그룹 이름 변경")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "변경 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 그룹",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<MemorizationGroupResponse> update(@Parameter(description = "그룹 id") Long id, @Valid @RequestBody MemorizationGroupRequest request);

	@Operation(summary = "암송 그룹 삭제", description = "그룹에 속한 암송 구절도 함께 삭제됩니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "삭제 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "403", description = "본인 소유가 아님",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 그룹",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> delete(@Parameter(description = "그룹 id") Long id);
}