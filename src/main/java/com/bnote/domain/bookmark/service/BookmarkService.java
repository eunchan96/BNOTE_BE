package com.bnote.domain.bookmark.service;

import com.bnote.domain.bible.entity.BibleBooks;
import com.bnote.domain.bible.exception.BibleException;
import com.bnote.domain.bookmark.dto.request.BookmarkToggleRequest;
import com.bnote.domain.bookmark.dto.response.BookmarkResponse;
import com.bnote.domain.bookmark.entity.BibleBookmark;
import com.bnote.domain.bookmark.repository.BibleBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

	private final BibleBookmarkRepository bibleBookmarkRepository;

	public List<BookmarkResponse> getByChapter(Long memberId, Integer bookId, Integer chapter) {
		validateBookId(bookId);
		return bibleBookmarkRepository.findActiveByMemberAndChapter(memberId, bookId, chapter)
			.stream()
			.map(BookmarkResponse::from)
			.toList();
	}

	@Transactional
	public BookmarkResponse toggle(
		Long memberId, Integer bookId, Integer chapter, Integer verse, BookmarkToggleRequest request
	) {
		validateBookId(bookId);

		BibleBookmark bookmark = bibleBookmarkRepository
			.findByMemberIdAndBookIdAndChapterAndVerse(memberId, bookId, chapter, verse)
			.orElseGet(() -> BibleBookmark.builder()
				.memberId(memberId)
				.bookId(bookId)
				.chapter(chapter)
				.verse(verse)
				.build()
			);

		bookmark.toggle(request.isBookmarked(), request.isHighlighted());

		if (bookmark.getId() == null && !bookmark.isEmpty()) {
			bookmark = bibleBookmarkRepository.save(bookmark);
		} else if (bookmark.getId() != null && bookmark.isEmpty()) {
			// 북마크도 하이라이트도 아니게 되면 더 이상 저장해둘 이유가 없음
			bibleBookmarkRepository.delete(bookmark);
		}

		return BookmarkResponse.from(bookmark);
	}

	private void validateBookId(Integer bookId) {
		if (bookId == null || bookId < 1 || bookId > BibleBooks.totalBookCount()) {
			throw BibleException.invalidBookId();
		}
	}
}