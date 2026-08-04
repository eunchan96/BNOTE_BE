package com.bnote.domain.mypage.readingprogress.controller;

import com.bnote.domain.mypage.readingprogress.dto.request.ReadingProgressRequest;
import com.bnote.domain.mypage.readingprogress.dto.response.ReadingProgressResponse;
import com.bnote.domain.mypage.readingprogress.facade.ReadingProgressFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReadingProgressController implements ReadingProgressControllerDocs {

	private final ReadingProgressFacade readingProgressFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<ReadingProgressResponse>> getAll() {
		return RsData.ok("성경읽기표 목록 조회 성공", readingProgressFacade.getAll(rq.getActorIdOrThrow()));
	}

	@PostMapping
	public RsData<ReadingProgressResponse> check(@Valid @RequestBody ReadingProgressRequest request) {
		ReadingProgressResponse response = readingProgressFacade.check(rq.getActorIdOrThrow(), request);
		return RsData.created("성경읽기표 체크 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> cancel(@PathVariable Long id) {
		readingProgressFacade.cancel(rq.getActorIdOrThrow(), id);
		return RsData.ok("성경읽기표 체크 취소 성공");
	}
}