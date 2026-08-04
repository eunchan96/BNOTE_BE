package com.bnote.domain.mypage.gratitude.controller;

import com.bnote.domain.mypage.gratitude.dto.request.GratitudeNoteRequest;
import com.bnote.domain.mypage.gratitude.dto.response.GratitudeNoteResponse;
import com.bnote.domain.mypage.gratitude.facade.GratitudeFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GratitudeController implements GratitudeControllerDocs {

	private final GratitudeFacade gratitudeFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<GratitudeNoteResponse>> getAll() {
		return RsData.ok("감사노트 목록 조회 성공", gratitudeFacade.getAll(rq.getActorIdOrThrow()));
	}

	@PostMapping
	public RsData<GratitudeNoteResponse> save(@Valid @RequestBody GratitudeNoteRequest request) {
		return RsData.ok("감사노트 등록/수정 성공", gratitudeFacade.save(rq.getActorIdOrThrow(), request));
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		gratitudeFacade.delete(rq.getActorIdOrThrow(), id);
		return RsData.ok("감사노트 삭제 성공");
	}
}