package com.bnote.domain.mypage.gratitude.facade;

import com.bnote.domain.mypage.gratitude.dto.request.GratitudeNoteRequest;
import com.bnote.domain.mypage.gratitude.dto.response.GratitudeNoteResponse;
import com.bnote.domain.mypage.gratitude.service.GratitudeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GratitudeFacade {

	private final GratitudeService gratitudeService;

	public List<GratitudeNoteResponse> getAll(Long memberId) {
		return gratitudeService.getAll(memberId);
	}

	public GratitudeNoteResponse save(Long memberId, GratitudeNoteRequest request) {
		return gratitudeService.save(memberId, request);
	}

	public void delete(Long memberId, Long id) {
		gratitudeService.delete(memberId, id);
	}
}