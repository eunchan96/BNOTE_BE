package com.bnote.domain.bible.dto.response;

import com.bnote.domain.bible.entity.BibleBooks;
import com.bnote.domain.bible.entity.BibleVerse;
import java.util.List;

public record BibleChapterResponse(
	Integer bookId,
	String bookName,
	Integer chapter,
	List<VerseItem> verses
) {
	public record VerseItem(
		Integer verse,
		String text,
		String title,
		String title2,
		String text2
	) {
		public static VerseItem from(BibleVerse verse) {
			return new VerseItem(verse.getVerse(), verse.getText(), verse.getTitle(), verse.getTitle2(), verse.getText2());
		}
	}

	public static BibleChapterResponse of(Integer bookId, Integer chapter, List<BibleVerse> verses) {
		return new BibleChapterResponse(
			bookId,
			BibleBooks.nameOf(bookId),
			chapter,
			verses.stream().map(VerseItem::from).toList()
		);
	}
}