package com.bnote.domain.mypage.verseofyear.controller;

import com.bnote.domain.mypage.verseofyear.dto.request.VerseOfYearRequest;
import com.bnote.domain.mypage.verseofyear.dto.response.VerseOfYearResponse;
import com.bnote.domain.mypage.verseofyear.facade.VerseOfYearFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VerseOfYearController implements VerseOfYearControllerDocs {

	private final VerseOfYearFacade verseOfYearFacade;
	private final Rq rq;

	@GetMapping("/{year}")
	public RsData<VerseOfYearResponse> getByYear(@PathVariable Integer year) {
		return RsData.ok("올해의 말씀 조회 성공", verseOfYearFacade.getByYear(rq.getActorIdOrThrow(), year));
	}

	@PostMapping
	public RsData<VerseOfYearResponse> save(@Valid @RequestBody VerseOfYearRequest request) {
		return RsData.ok("올해의 말씀 등록/수정 성공", verseOfYearFacade.save(rq.getActorIdOrThrow(), request));
	}
}