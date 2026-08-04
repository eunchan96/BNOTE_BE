package com.bnote.domain.mypage.recentactivity.service;

import com.bnote.domain.mypage.recentactivity.dto.request.RecentChapterViewRequest;
import com.bnote.domain.mypage.recentactivity.dto.response.RecentChapterViewResponse;
import com.bnote.domain.mypage.recentactivity.entity.RecentChapterView;
import com.bnote.domain.mypage.recentactivity.repository.RecentChapterViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecentChapterViewService {

	private static final int DEFAULT_LIMIT = 10;

	private final RecentChapterViewRepository recentChapterViewRepository;

	public List<RecentChapterViewResponse> getRecent(Long memberId, Integer limit) {
		int size = (limit == null || limit <= 0) ? DEFAULT_LIMIT : limit;
		return recentChapterViewRepository.findByMemberIdOrderByViewedAtDesc(memberId, PageRequest.of(0, size))
			.stream()
			.map(RecentChapterViewResponse::from)
			.toList();
	}

	@Transactional
	public RecentChapterViewResponse record(Long memberId, RecentChapterViewRequest request) {
		RecentChapterView view = recentChapterViewRepository
			.findByMemberIdAndBookIdAndChapter(memberId, request.bookId(), request.chapter())
			.map(existing -> {
				existing.touch();
				return existing;
			})
			.orElseGet(() -> recentChapterViewRepository.save(
				RecentChapterView.builder().memberId(memberId).bookId(request.bookId()).chapter(request.chapter()).build()
			));

		return RecentChapterViewResponse.from(view);
	}
}