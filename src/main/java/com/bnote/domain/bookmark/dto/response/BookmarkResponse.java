package com.bnote.domain.bookmark.dto.response;

import com.bnote.domain.bookmark.entity.BibleBookmark;
import java.time.LocalDateTime;

public record BookmarkResponse(
	Long id,
	Integer bookId,
	Integer chapter,
	Integer verse,
	boolean isBookmarked,
	boolean isHighlighted,
	LocalDateTime updatedAt
) {
	public static BookmarkResponse from(BibleBookmark bookmark) {
		return new BookmarkResponse(
			bookmark.getId(),
			bookmark.getBookId(),
			bookmark.getChapter(),
			bookmark.getVerse(),
			bookmark.isBookmarked(),
			bookmark.isHighlighted(),
			bookmark.getModifyDate()
		);
	}
}