package com.bnote.domain.mypage.profile.controller;

import com.bnote.domain.mypage.profile.dto.request.ProfileRequest;
import com.bnote.domain.mypage.profile.dto.response.ProfileResponse;
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
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Profile", description = "마이페이지 프로필 API")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("api/v1/members/me/profile")
public interface ProfileControllerDocs {

	@Operation(summary = "프로필 조회", description = "아직 등록된 적 없으면 빈 값으로 응답합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "조회 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<ProfileResponse> getProfile();

	@Operation(summary = "프로필 수정")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "수정 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<ProfileResponse> update(@Valid @RequestBody ProfileRequest request);

	@Operation(summary = "프로필 사진 변경", description = "multipart/form-data로 이미지 파일을 업로드합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "변경 성공",
			content = @Content(schema = @Schema(implementation = RsData.class)))
	})
	RsData<ProfileResponse> updatePhoto(MultipartFile file);
}