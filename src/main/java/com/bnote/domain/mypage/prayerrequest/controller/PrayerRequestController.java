package com.bnote.domain.mypage.prayerrequest.controller;

import com.bnote.domain.mypage.prayerrequest.dto.request.PrayerRequestAnswerRequest;
import com.bnote.domain.mypage.prayerrequest.dto.request.PrayerRequestCreateRequest;
import com.bnote.domain.mypage.prayerrequest.dto.response.PrayerRequestResponse;
import com.bnote.domain.mypage.prayerrequest.facade.PrayerRequestFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PrayerRequestController implements PrayerRequestControllerDocs {

	private final PrayerRequestFacade prayerRequestFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<PrayerRequestResponse>> getAll() {
		return RsData.ok("기도제목 목록 조회 성공", prayerRequestFacade.getAll(rq.getActorIdOrThrow()));
	}

	@PostMapping
	public RsData<PrayerRequestResponse> create(@Valid @RequestBody PrayerRequestCreateRequest request) {
		PrayerRequestResponse response = prayerRequestFacade.create(rq.getActorIdOrThrow(), request);
		return RsData.created("기도제목 등록 성공", response);
	}

	@PutMapping("/{id}/answer")
	public RsData<PrayerRequestResponse> markAnswered(@PathVariable Long id, @Valid @RequestBody PrayerRequestAnswerRequest request) {
		PrayerRequestResponse response = prayerRequestFacade.markAnswered(rq.getActorIdOrThrow(), id, request);
		return RsData.ok("기도제목 응답 체크 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		prayerRequestFacade.delete(rq.getActorIdOrThrow(), id);
		return RsData.ok("기도제목 삭제 성공");
	}
}