package com.bnote.domain.bible.partialhighlight.controller;

import com.bnote.domain.bible.partialhighlight.dto.request.PartialHighlightRequest;
import com.bnote.domain.bible.partialhighlight.dto.response.PartialHighlightResponse;
import com.bnote.domain.bible.partialhighlight.facade.PartialHighlightFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PartialHighlightController implements PartialHighlightControllerDocs {

	private final PartialHighlightFacade partialHighlightFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<PartialHighlightResponse>> getByLocation(
		@RequestParam Integer bookId,
		@RequestParam Integer chapter,
		@RequestParam(required = false) Integer verse
	) {
		List<PartialHighlightResponse> response =
			partialHighlightFacade.getByLocation(rq.getActorIdOrThrow(), bookId, chapter, verse);
		return RsData.ok("부분 하이라이트 목록 조회 성공", response);
	}

	@PostMapping
	public RsData<PartialHighlightResponse> create(@Valid @RequestBody PartialHighlightRequest request) {
		PartialHighlightResponse response = partialHighlightFacade.create(rq.getActorIdOrThrow(), request);
		return RsData.created("부분 하이라이트 등록 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		partialHighlightFacade.delete(rq.getActorIdOrThrow(), id);
		return RsData.ok("부분 하이라이트 삭제 성공");
	}
}