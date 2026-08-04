package com.bnote.domain.bible.scrap.service;

import com.bnote.domain.bible.scrap.dto.request.ScrapGroupRequest;
import com.bnote.domain.bible.scrap.dto.response.ScrapGroupResponse;
import com.bnote.domain.bible.scrap.entity.ScrapGroup;
import com.bnote.domain.bible.scrap.exception.ScrapException;
import com.bnote.domain.bible.scrap.repository.ScrapGroupRepository;
import com.bnote.domain.bible.scrap.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScrapGroupService {

	private final ScrapGroupRepository scrapGroupRepository;
	private final ScrapRepository scrapRepository;

	public List<ScrapGroupResponse> getGroups(Long memberId) {
		return scrapGroupRepository.findByMemberIdOrderBySortOrderAsc(memberId)
			.stream()
			.map(ScrapGroupResponse::from)
			.toList();
	}

	@Transactional
	public ScrapGroupResponse create(Long memberId, ScrapGroupRequest request) {
		ScrapGroup group = ScrapGroup.builder()
			.memberId(memberId)
			.name(request.name())
			.sortOrder(request.sortOrder() == null ? 0 : request.sortOrder())
			.build();

		return ScrapGroupResponse.from(scrapGroupRepository.save(group));
	}

	@Transactional
	public ScrapGroupResponse rename(Long memberId, Long groupId, ScrapGroupRequest request) {
		ScrapGroup group = findOwnedGroup(memberId, groupId);
		group.rename(request.name());
		return ScrapGroupResponse.from(group);
	}

	@Transactional
	public void delete(Long memberId, Long groupId) {
		ScrapGroup group = findOwnedGroup(memberId, groupId);
		scrapRepository.deleteAll(scrapRepository.findByGroupIdOrderByCreateDateDesc(group.getId()));
		scrapGroupRepository.delete(group);
	}

	ScrapGroup findOwnedGroup(Long memberId, Long groupId) {
		ScrapGroup group = scrapGroupRepository.findById(groupId)
			.orElseThrow(ScrapException::groupNotFound);

		if (!group.getMemberId().equals(memberId)) {
			throw ScrapException.accessDenied();
		}
		return group;
	}
}