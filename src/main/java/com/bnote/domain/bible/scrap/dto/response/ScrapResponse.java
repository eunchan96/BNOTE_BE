package com.bnote.domain.bible.scrap.dto.response;

import com.bnote.domain.bible.scrap.entity.Scrap;
import java.time.LocalDateTime;

public record ScrapResponse(
	Long id,
	Long groupId,
	Integer bookId,
	Integer chapter,
	Integer startVerse,
	Integer endVerse,
	String verseText,
	LocalDateTime createdAt
) {
	public static ScrapResponse from(Scrap scrap) {
		return new ScrapResponse(
			scrap.getId(), scrap.getGroupId(), scrap.getBookId(), scrap.getChapter(),
			scrap.getStartVerse(), scrap.getEndVerse(), scrap.getVerseText(), scrap.getCreateDate()
		);
	}
}