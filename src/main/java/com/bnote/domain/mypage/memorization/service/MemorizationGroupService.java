package com.bnote.domain.mypage.memorization.service;

import com.bnote.domain.mypage.memorization.dto.request.MemorizationGroupRequest;
import com.bnote.domain.mypage.memorization.dto.response.MemorizationGroupResponse;
import com.bnote.domain.mypage.memorization.entity.MemorizationGroup;
import com.bnote.domain.mypage.memorization.exception.MemorizationException;
import com.bnote.domain.mypage.memorization.repository.MemorizationGroupRepository;
import com.bnote.domain.mypage.memorization.repository.MemorizationVerseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemorizationGroupService {

	private final MemorizationGroupRepository memorizationGroupRepository;
	private final MemorizationVerseRepository memorizationVerseRepository;

	public List<MemorizationGroupResponse> getAll(Long memberId) {
		return memorizationGroupRepository.findByMemberIdOrderBySortOrderAsc(memberId)
			.stream()
			.map(MemorizationGroupResponse::from)
			.toList();
	}

	@Transactional
	public MemorizationGroupResponse create(Long memberId, MemorizationGroupRequest request) {
		MemorizationGroup group = MemorizationGroup.builder()
			.memberId(memberId)
			.name(request.name())
			.sortOrder(request.sortOrder() == null ? 0 : request.sortOrder())
			.build();
		return MemorizationGroupResponse.from(memorizationGroupRepository.save(group));
	}

	@Transactional
	public MemorizationGroupResponse rename(Long memberId, Long id, MemorizationGroupRequest request) {
		MemorizationGroup group = findOwned(memberId, id);
		group.rename(request.name());
		return MemorizationGroupResponse.from(group);
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		MemorizationGroup group = findOwned(memberId, id);
		memorizationVerseRepository.deleteAll(memorizationVerseRepository.findByGroupIdOrderByCreateDateDesc(group.getId()));
		memorizationGroupRepository.delete(group);
	}

	MemorizationGroup findOwned(Long memberId, Long id) {
		MemorizationGroup group = memorizationGroupRepository.findById(id)
			.orElseThrow(MemorizationException::groupNotFound);
		if (!group.getMemberId().equals(memberId)) {
			throw MemorizationException.accessDenied();
		}
		return group;
	}
}