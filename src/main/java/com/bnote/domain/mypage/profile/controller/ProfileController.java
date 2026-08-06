package com.bnote.domain.mypage.profile.controller;

import com.bnote.domain.mypage.profile.dto.request.ProfileRequest;
import com.bnote.domain.mypage.profile.dto.response.ProfileResponse;
import com.bnote.domain.mypage.profile.facade.ProfileFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProfileController implements ProfileControllerDocs {

	private final ProfileFacade profileFacade;
	private final Rq rq;

	@GetMapping
	public RsData<ProfileResponse> getProfile() {
		return RsData.ok("프로필 조회 성공", profileFacade.getProfile(rq.getActorIdOrThrow()));
	}

	@PutMapping
	public RsData<ProfileResponse> update(@Valid @RequestBody ProfileRequest request) {
		return RsData.ok("프로필 수정 성공", profileFacade.updateProfile(rq.getActorIdOrThrow(), request));
	}

	@PostMapping(value = "/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RsData<ProfileResponse> updatePhoto(@RequestParam("file") MultipartFile file) {
		return RsData.ok("프로필 사진 변경 성공", profileFacade.updatePhoto(rq.getActorIdOrThrow(), file));
	}
}