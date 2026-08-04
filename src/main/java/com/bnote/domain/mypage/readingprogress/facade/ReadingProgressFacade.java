package com.bnote.domain.mypage.readingprogress.facade;

import com.bnote.domain.mypage.readingprogress.dto.request.ReadingProgressRequest;
import com.bnote.domain.mypage.readingprogress.dto.response.ReadingProgressResponse;
import com.bnote.domain.mypage.readingprogress.service.ReadingProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadingProgressFacade {

	private final ReadingProgressService readingProgressService;

	public List<ReadingProgressResponse> getAll(Long memberId) {
		return readingProgressService.getAll(memberId);
	}

	public ReadingProgressResponse check(Long memberId, ReadingProgressRequest request) {
		return readingProgressService.check(memberId, request);
	}

	public void cancel(Long memberId, Long id) {
		readingProgressService.cancel(memberId, id);
	}
}