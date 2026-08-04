package com.bnote.domain.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record ApplicationBibleRefRequest(
	@NotNull Integer startBookId,
	@NotNull Integer startChapter,
	@NotNull Integer startVerse,
	@NotNull Integer endBookId,
	@NotNull Integer endChapter,
	@NotNull Integer endVerse,
	boolean isChapterOnly
) {
}