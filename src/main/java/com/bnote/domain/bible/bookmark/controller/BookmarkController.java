package com.bnote.domain.bible.bookmark.controller;

import com.bnote.domain.bible.bookmark.dto.request.BookmarkToggleRequest;
import com.bnote.domain.bible.bookmark.dto.response.BookmarkResponse;
import com.bnote.domain.bible.bookmark.facade.BookmarkFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookmarkController implements BookmarkControllerDocs {

	private final BookmarkFacade bookmarkFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<BookmarkResponse>> getByChapter(
		@RequestParam Integer bookId,
		@RequestParam Integer chapter
	) {
		List<BookmarkResponse> response = bookmarkFacade.getByChapter(rq.getActorIdOrThrow(), bookId, chapter);
		return RsData.ok("북마크·하이라이트 목록 조회 성공", response);
	}

	@PutMapping("/{bookId}/{chapter}/{verse}")
	public RsData<BookmarkResponse> toggle(
		@PathVariable Integer bookId,
		@PathVariable Integer chapter,
		@PathVariable Integer verse,
		@Valid @RequestBody BookmarkToggleRequest request
	) {
		BookmarkResponse response = bookmarkFacade.toggle(rq.getActorIdOrThrow(), bookId, chapter, verse, request);
		return RsData.ok("북마크·하이라이트 토글 성공", response);
	}
}