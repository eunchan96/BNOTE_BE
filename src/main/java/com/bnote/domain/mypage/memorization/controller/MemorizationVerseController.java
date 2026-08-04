package com.bnote.domain.mypage.memorization.controller;

import com.bnote.domain.mypage.memorization.dto.request.MemorizationReviewRequest;
import com.bnote.domain.mypage.memorization.dto.request.MemorizationVerseRequest;
import com.bnote.domain.mypage.memorization.dto.response.MemorizationVerseResponse;
import com.bnote.domain.mypage.memorization.facade.MemorizationFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemorizationVerseController implements MemorizationVerseControllerDocs {

	private final MemorizationFacade memorizationFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<MemorizationVerseResponse>> getByGroup(@RequestParam Long groupId) {
		return RsData.ok("암송 구절 목록 조회 성공", memorizationFacade.getVerses(rq.getActorIdOrThrow(), groupId));
	}

	@PostMapping
	public RsData<MemorizationVerseResponse> create(@Valid @RequestBody MemorizationVerseRequest request) {
		MemorizationVerseResponse response = memorizationFacade.createVerse(rq.getActorIdOrThrow(), request);
		return RsData.created("암송 구절 등록 성공", response);
	}

	@PutMapping("/{id}/review")
	public RsData<MemorizationVerseResponse> review(@PathVariable Long id, @Valid @RequestBody MemorizationReviewRequest request) {
		MemorizationVerseResponse response = memorizationFacade.review(rq.getActorIdOrThrow(), id, request);
		return RsData.ok("암송 연습 기록 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		memorizationFacade.deleteVerse(rq.getActorIdOrThrow(), id);
		return RsData.ok("암송 구절 삭제 성공");
	}
}