package com.bnote.domain.bible.memo.controller;

import com.bnote.domain.bible.memo.dto.request.VerseMemoRequest;
import com.bnote.domain.bible.memo.dto.request.VerseMemoUpdateRequest;
import com.bnote.domain.bible.memo.dto.response.VerseMemoResponse;
import com.bnote.domain.bible.memo.facade.MemoFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VerseMemoController implements VerseMemoControllerDocs {

	private final MemoFacade memoFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<VerseMemoResponse>> getByChapter(
		@RequestParam Integer bookId,
		@RequestParam Integer chapter
	) {
		return RsData.ok("구절 메모 목록 조회 성공", memoFacade.getVerseMemos(rq.getActorIdOrThrow(), bookId, chapter));
	}

	@PostMapping
	public RsData<VerseMemoResponse> create(@Valid @RequestBody VerseMemoRequest request) {
		VerseMemoResponse response = memoFacade.createVerseMemo(rq.getActorIdOrThrow(), request);
		return RsData.created("구절 메모 등록 성공", response);
	}

	@PutMapping("/{id}")
	public RsData<VerseMemoResponse> update(
		@PathVariable Long id,
		@Valid @RequestBody VerseMemoUpdateRequest request
	) {
		VerseMemoResponse response = memoFacade.updateVerseMemo(rq.getActorIdOrThrow(), id, request);
		return RsData.ok("구절 메모 수정 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		memoFacade.deleteVerseMemo(rq.getActorIdOrThrow(), id);
		return RsData.ok("구절 메모 삭제 성공");
	}
}