package com.bnote.domain.bible.bookmark.facade;

import com.bnote.domain.bible.bookmark.dto.request.BookmarkToggleRequest;
import com.bnote.domain.bible.bookmark.dto.response.BookmarkResponse;
import com.bnote.domain.bible.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkFacade {

	private final BookmarkService bookmarkService;

	public List<BookmarkResponse> getByChapter(Long memberId, Integer bookId, Integer chapter) {
		return bookmarkService.getByChapter(memberId, bookId, chapter);
	}

	public BookmarkResponse toggle(
		Long memberId, Integer bookId, Integer chapter, Integer verse, BookmarkToggleRequest request
	) {
		return bookmarkService.toggle(memberId, bookId, chapter, verse, request);
	}
}