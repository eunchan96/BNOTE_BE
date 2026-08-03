package com.bnote.domain.bible.bible.dto.response;

import com.bnote.domain.bible.bible.entity.BibleBooks;
import com.bnote.domain.bible.bible.entity.BibleVerse;
import java.util.List;

public record BibleSearchResponse(
	List<Item> results
) {
	public record Item(
		Integer bookId,
		String bookName,
		Integer chapter,
		Integer verse,
		String text
	) {
		public static Item from(BibleVerse v) {
			return new Item(v.getBookId(), BibleBooks.nameOf(v.getBookId()), v.getChapter(), v.getVerse(), v.getText());
		}
	}

	public static BibleSearchResponse of(List<BibleVerse> verses) {
		return new BibleSearchResponse(verses.stream().map(Item::from).toList());
	}
}