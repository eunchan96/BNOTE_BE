package com.bnote.domain.mypage.prayerrequest.service;

import com.bnote.domain.mypage.prayerrequest.dto.request.PrayerRequestAnswerRequest;
import com.bnote.domain.mypage.prayerrequest.dto.request.PrayerRequestCreateRequest;
import com.bnote.domain.mypage.prayerrequest.dto.response.PrayerRequestResponse;
import com.bnote.domain.mypage.prayerrequest.entity.PrayerRequest;
import com.bnote.domain.mypage.prayerrequest.exception.PrayerRequestException;
import com.bnote.domain.mypage.prayerrequest.repository.PrayerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrayerRequestService {

	private final PrayerRequestRepository prayerRequestRepository;

	public List<PrayerRequestResponse> getAll(Long memberId) {
		return prayerRequestRepository.findByMemberIdOrderByCreateDateDesc(memberId)
			.stream()
			.map(PrayerRequestResponse::from)
			.toList();
	}

	@Transactional
	public PrayerRequestResponse create(Long memberId, PrayerRequestCreateRequest request) {
		PrayerRequest prayerRequest = PrayerRequest.builder().memberId(memberId).content(request.content()).build();
		return PrayerRequestResponse.from(prayerRequestRepository.save(prayerRequest));
	}

	@Transactional
	public PrayerRequestResponse markAnswered(Long memberId, Long id, PrayerRequestAnswerRequest request) {
		PrayerRequest prayerRequest = findOwned(memberId, id);
		prayerRequest.markAnswered(request.answered());
		return PrayerRequestResponse.from(prayerRequest);
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		prayerRequestRepository.delete(findOwned(memberId, id));
	}

	private PrayerRequest findOwned(Long memberId, Long id) {
		PrayerRequest prayerRequest = prayerRequestRepository.findById(id)
			.orElseThrow(PrayerRequestException::notFound);
		if (!prayerRequest.getMemberId().equals(memberId)) {
			throw PrayerRequestException.accessDenied();
		}
		return prayerRequest;
	}
}