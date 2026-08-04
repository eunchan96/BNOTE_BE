package com.bnote.domain.mypage.recentactivity.facade;

import com.bnote.domain.mypage.recentactivity.dto.request.RecentChapterViewRequest;
import com.bnote.domain.mypage.recentactivity.dto.response.RecentChapterViewResponse;
import com.bnote.domain.mypage.recentactivity.service.RecentChapterViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecentChapterViewFacade {

	private final RecentChapterViewService recentChapterViewService;

	public List<RecentChapterViewResponse> getRecent(Long memberId, Integer limit) {
		return recentChapterViewService.getRecent(memberId, limit);
	}

	public RecentChapterViewResponse record(Long memberId, RecentChapterViewRequest request) {
		return recentChapterViewService.record(memberId, request);
	}
}