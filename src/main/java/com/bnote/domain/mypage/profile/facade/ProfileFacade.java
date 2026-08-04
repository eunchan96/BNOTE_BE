package com.bnote.domain.mypage.profile.facade;

import com.bnote.domain.mypage.profile.dto.request.ProfileRequest;
import com.bnote.domain.mypage.profile.dto.response.ProfileResponse;
import com.bnote.domain.mypage.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileFacade {

	private final ProfileService profileService;

	public ProfileResponse getProfile(Long memberId) {
		return profileService.getProfile(memberId);
	}

	public ProfileResponse updateProfile(Long memberId, ProfileRequest request) {
		return profileService.update(memberId, request);
	}

	public ProfileResponse updatePhoto(Long memberId, MultipartFile file) {
		return profileService.updatePhoto(memberId, file);
	}
}