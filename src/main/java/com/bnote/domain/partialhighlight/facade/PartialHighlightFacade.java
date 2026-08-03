package com.bnote.domain.partialhighlight.facade;

import com.bnote.domain.partialhighlight.dto.request.PartialHighlightRequest;
import com.bnote.domain.partialhighlight.dto.response.PartialHighlightResponse;
import com.bnote.domain.partialhighlight.service.PartialHighlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartialHighlightFacade {

	private final PartialHighlightService partialHighlightService;

	public List<PartialHighlightResponse> getByLocation(Long memberId, Integer bookId, Integer chapter, Integer verse) {
		return partialHighlightService.getByLocation(memberId, bookId, chapter, verse);
	}

	public PartialHighlightResponse create(Long memberId, PartialHighlightRequest request) {
		return partialHighlightService.create(memberId, request);
	}

	public void delete(Long memberId, Long id) {
		partialHighlightService.delete(memberId, id);
	}
}