package com.bnote.domain.mypage.prayerrequest.facade;

import com.bnote.domain.mypage.prayerrequest.dto.request.PrayerRequestAnswerRequest;
import com.bnote.domain.mypage.prayerrequest.dto.request.PrayerRequestCreateRequest;
import com.bnote.domain.mypage.prayerrequest.dto.response.PrayerRequestResponse;
import com.bnote.domain.mypage.prayerrequest.service.PrayerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrayerRequestFacade {

	private final PrayerRequestService prayerRequestService;

	public List<PrayerRequestResponse> getAll(Long memberId) {
		return prayerRequestService.getAll(memberId);
	}

	public PrayerRequestResponse create(Long memberId, PrayerRequestCreateRequest request) {
		return prayerRequestService.create(memberId, request);
	}

	public PrayerRequestResponse markAnswered(Long memberId, Long id, PrayerRequestAnswerRequest request) {
		return prayerRequestService.markAnswered(memberId, id, request);
	}

	public void delete(Long memberId, Long id) {
		prayerRequestService.delete(memberId, id);
	}
}