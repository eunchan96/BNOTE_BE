package com.bnote.domain.bible.memo.controller;

import com.bnote.domain.bible.memo.dto.request.WordMemoRequest;
import com.bnote.domain.bible.memo.dto.request.WordMemoUpdateRequest;
import com.bnote.domain.bible.memo.dto.response.WordMemoResponse;
import com.bnote.domain.bible.memo.facade.MemoFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WordMemoController implements WordMemoControllerDocs {

	private final MemoFacade memoFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<WordMemoResponse>> getByChapter(
		@RequestParam Integer bookId,
		@RequestParam Integer chapter
	) {
		return RsData.ok("단어 메모 목록 조회 성공", memoFacade.getWordMemos(rq.getActorIdOrThrow(), bookId, chapter));
	}

	@PostMapping
	public RsData<WordMemoResponse> create(@Valid @RequestBody WordMemoRequest request) {
		WordMemoResponse response = memoFacade.createWordMemo(rq.getActorIdOrThrow(), request);
		return RsData.created("단어 메모 등록 성공", response);
	}

	@PutMapping("/{id}")
	public RsData<WordMemoResponse> update(
		@PathVariable Long id,
		@Valid @RequestBody WordMemoUpdateRequest request
	) {
		WordMemoResponse response = memoFacade.updateWordMemo(rq.getActorIdOrThrow(), id, request);
		return RsData.ok("단어 메모 수정 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		memoFacade.deleteWordMemo(rq.getActorIdOrThrow(), id);
		return RsData.ok("단어 메모 삭제 성공");
	}
}