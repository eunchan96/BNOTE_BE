package com.bnote.domain.mypage.memorization.facade;

import com.bnote.domain.mypage.memorization.dto.request.MemorizationGroupRequest;
import com.bnote.domain.mypage.memorization.dto.request.MemorizationReviewRequest;
import com.bnote.domain.mypage.memorization.dto.request.MemorizationVerseRequest;
import com.bnote.domain.mypage.memorization.dto.response.MemorizationGroupResponse;
import com.bnote.domain.mypage.memorization.dto.response.MemorizationVerseResponse;
import com.bnote.domain.mypage.memorization.service.MemorizationGroupService;
import com.bnote.domain.mypage.memorization.service.MemorizationVerseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemorizationFacade {

	private final MemorizationGroupService memorizationGroupService;
	private final MemorizationVerseService memorizationVerseService;

	public List<MemorizationGroupResponse> getGroups(Long memberId) {
		return memorizationGroupService.getAll(memberId);
	}

	public MemorizationGroupResponse createGroup(Long memberId, MemorizationGroupRequest request) {
		return memorizationGroupService.create(memberId, request);
	}

	public MemorizationGroupResponse renameGroup(Long memberId, Long id, MemorizationGroupRequest request) {
		return memorizationGroupService.rename(memberId, id, request);
	}

	public void deleteGroup(Long memberId, Long id) {
		memorizationGroupService.delete(memberId, id);
	}

	public List<MemorizationVerseResponse> getVerses(Long memberId, Long groupId) {
		return memorizationVerseService.getByGroup(memberId, groupId);
	}

	public MemorizationVerseResponse createVerse(Long memberId, MemorizationVerseRequest request) {
		return memorizationVerseService.create(memberId, request);
	}

	public MemorizationVerseResponse review(Long memberId, Long id, MemorizationReviewRequest request) {
		return memorizationVerseService.review(memberId, id, request);
	}

	public void deleteVerse(Long memberId, Long id) {
		memorizationVerseService.delete(memberId, id);
	}
}