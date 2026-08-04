package com.bnote.domain.mypage.profile.service;

import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.exception.MemberException;
import com.bnote.domain.member.repository.MemberRepository;
import com.bnote.domain.mypage.profile.dto.request.ProfileRequest;
import com.bnote.domain.mypage.profile.dto.response.ProfileResponse;
import com.bnote.global.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

	private final MemberRepository memberRepository;
	private final FileStorageService fileStorageService;

	public ProfileResponse getProfile(Long memberId) {
		return ProfileResponse.from(findMember(memberId));
	}

	@Transactional
	public ProfileResponse update(Long memberId, ProfileRequest request) {
		Member member = findMember(memberId);
		member.updateMyPageProfile(request.name(), request.church(), request.department(), request.position());
		return ProfileResponse.from(member);
	}

	@Transactional
	public ProfileResponse updatePhoto(Long memberId, MultipartFile file) {
		Member member = findMember(memberId);
		String photoUrl = fileStorageService.store(file, "profiles/" + memberId);
		member.updatePhotoUrl(photoUrl);
		return ProfileResponse.from(member);
	}

	private Member findMember(Long memberId) {
		return memberRepository.findById(memberId).orElseThrow(MemberException::notFound);
	}
}