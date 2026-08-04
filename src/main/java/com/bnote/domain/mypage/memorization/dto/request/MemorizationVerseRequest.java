package com.bnote.domain.mypage.memorization.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemorizationVerseRequest(
	@NotNull Long groupId,
	@NotNull Integer startBookId,
	@NotNull Integer startChapter,
	@NotNull Integer startVerse,
	@NotNull Integer endBookId,
	@NotNull Integer endChapter,
	@NotNull Integer endVerse,
	String translation,
	String note
) {
}