package com.bnote.domain.member.controller;

import com.bnote.domain.member.dto.request.ReissueRequest;
import com.bnote.domain.member.dto.request.SocialLoginRequest;
import com.bnote.domain.member.dto.response.TokenResponse;
import com.bnote.global.response.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 인증 관련 REST API
 * - 카카오/구글 소셜 로그인
 * - JWT access/refresh token 재발급 및 로그아웃
 */
@Tag(name = "Auth", description = "인증 관련 API")
@RequestMapping("api/v1/auth")
public interface AuthControllerDocs {

	@Operation(summary = "카카오 로그인", description = "프론트에서 카카오 SDK로 받은 authCode로 로그인/회원가입을 처리합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "기존 회원 로그인 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "201", description = "신규 회원가입 후 로그인 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "400", description = "카카오 인증 실패",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<TokenResponse> loginKakao(@Valid @RequestBody SocialLoginRequest request);

	@Operation(summary = "구글 로그인", description = "프론트에서 구글 SDK로 받은 authCode로 로그인/회원가입을 처리합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "기존 회원 로그인 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "201", description = "신규 회원가입 후 로그인 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "400", description = "구글 인증 실패",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<TokenResponse> loginGoogle(@Valid @RequestBody SocialLoginRequest request);

	@Operation(summary = "토큰 재발급", description = "refresh token으로 새 access/refresh token을 발급받습니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "재발급 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "401", description = "유효하지 않거나 일치하지 않는 refresh token",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<TokenResponse> reissue(@Valid @RequestBody ReissueRequest request);

	@Operation(summary = "로그아웃", description = "저장된 refresh token을 제거합니다. 로그인 상태여야 합니다.")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그아웃 성공",
			content = @Content(schema = @Schema(implementation = RsData.class))),
		@ApiResponse(responseCode = "401", description = "로그인이 필요함",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<Void> logout();
}