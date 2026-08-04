package com.bnote.domain.mypage.recentactivity.controller;

import com.bnote.domain.mypage.recentactivity.dto.request.RecentChapterViewRequest;
import com.bnote.domain.mypage.recentactivity.dto.response.RecentChapterViewResponse;
import com.bnote.domain.mypage.recentactivity.facade.RecentChapterViewFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecentChapterViewController implements RecentChapterViewControllerDocs {

	private final RecentChapterViewFacade recentChapterViewFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<RecentChapterViewResponse>> getRecent(@RequestParam(required = false) Integer limit) {
		return RsData.ok("최근 열람 장 목록 조회 성공", recentChapterViewFacade.getRecent(rq.getActorIdOrThrow(), limit));
	}

	@PostMapping
	public RsData<RecentChapterViewResponse> record(@Valid @RequestBody RecentChapterViewRequest request) {
		RecentChapterViewResponse response = recentChapterViewFacade.record(rq.getActorIdOrThrow(), request);
		return RsData.created("열람 기록 성공", response);
	}
}