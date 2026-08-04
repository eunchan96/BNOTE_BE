package com.bnote.domain.member.controller;

import com.bnote.domain.member.dto.response.MemberResponse;
import com.bnote.global.response.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 회원 관련 REST API
 * - 내 정보 조회 / 회원 탈퇴
 */
@Tag(name = "Member", description = "회원 관련 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/members")
public interface MemberControllerDocs {

	@Operation(summary = "내 정보 조회", description = "로그인한 회원의 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "401", description = "로그인이 필요함",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 회원",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<MemberResponse> getMe();

	@Operation(summary = "회원 탈퇴", description = "로그인한 회원을 탈퇴 처리합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "탈퇴 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "401", description = "로그인이 필요함",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> withdraw();
}