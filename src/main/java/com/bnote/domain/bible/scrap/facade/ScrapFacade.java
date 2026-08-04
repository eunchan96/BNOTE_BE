package com.bnote.domain.bible.scrap.facade;

import com.bnote.domain.bible.scrap.dto.request.ScrapGroupRequest;
import com.bnote.domain.bible.scrap.dto.request.ScrapRequest;
import com.bnote.domain.bible.scrap.dto.response.ScrapGroupResponse;
import com.bnote.domain.bible.scrap.dto.response.ScrapResponse;
import com.bnote.domain.bible.scrap.service.ScrapGroupService;
import com.bnote.domain.bible.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapFacade {

	private final ScrapGroupService scrapGroupService;
	private final ScrapService scrapService;

	public List<ScrapGroupResponse> getGroups(Long memberId) {
		return scrapGroupService.getGroups(memberId);
	}

	public ScrapGroupResponse createGroup(Long memberId, ScrapGroupRequest request) {
		return scrapGroupService.create(memberId, request);
	}

	public ScrapGroupResponse renameGroup(Long memberId, Long groupId, ScrapGroupRequest request) {
		return scrapGroupService.rename(memberId, groupId, request);
	}

	public void deleteGroup(Long memberId, Long groupId) {
		scrapGroupService.delete(memberId, groupId);
	}

	public List<ScrapResponse> getScraps(Long memberId, Long groupId) {
		return scrapService.getByGroup(memberId, groupId);
	}

	public ScrapResponse createScrap(Long memberId, ScrapRequest request) {
		return scrapService.create(memberId, request);
	}

	public void deleteScrap(Long memberId, Long id) {
		scrapService.delete(memberId, id);
	}
}