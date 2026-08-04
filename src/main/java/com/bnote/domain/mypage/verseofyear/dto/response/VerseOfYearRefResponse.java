package com.bnote.domain.mypage.verseofyear.dto.response;

import com.bnote.domain.mypage.verseofyear.entity.VerseOfYearRef;

public record VerseOfYearRefResponse(
	Long id,
	Integer startBookId,
	Integer startChapter,
	Integer startVerse,
	Integer endBookId,
	Integer endChapter,
	Integer endVerse,
	String verseText
) {
	public static VerseOfYearRefResponse from(VerseOfYearRef ref) {
		return new VerseOfYearRefResponse(
			ref.getId(), ref.getStartBookId(), ref.getStartChapter(), ref.getStartVerse(),
			ref.getEndBookId(), ref.getEndChapter(), ref.getEndVerse(), ref.getVerseText()
		);
	}
}