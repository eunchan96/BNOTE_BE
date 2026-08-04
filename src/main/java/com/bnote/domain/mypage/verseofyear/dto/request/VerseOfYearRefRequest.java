package com.bnote.domain.mypage.verseofyear.dto.request;

import jakarta.validation.constraints.NotNull;

public record VerseOfYearRefRequest(
	@NotNull Integer startBookId,
	@NotNull Integer startChapter,
	@NotNull Integer startVerse,
	@NotNull Integer endBookId,
	@NotNull Integer endChapter,
	@NotNull Integer endVerse,
	String translation
) {
}