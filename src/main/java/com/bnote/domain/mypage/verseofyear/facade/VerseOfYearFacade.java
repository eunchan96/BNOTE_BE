package com.bnote.domain.mypage.verseofyear.facade;

import com.bnote.domain.mypage.verseofyear.dto.request.VerseOfYearRequest;
import com.bnote.domain.mypage.verseofyear.dto.response.VerseOfYearResponse;
import com.bnote.domain.mypage.verseofyear.service.VerseOfYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerseOfYearFacade {

	private final VerseOfYearService verseOfYearService;

	public VerseOfYearResponse getByYear(Long memberId, Integer year) {
		return verseOfYearService.getByYear(memberId, year);
	}

	public VerseOfYearResponse save(Long memberId, VerseOfYearRequest request) {
		return verseOfYearService.save(memberId, request);
	}
}