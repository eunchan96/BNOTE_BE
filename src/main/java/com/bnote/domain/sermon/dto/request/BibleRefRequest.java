package com.bnote.domain.sermon.dto.request;

import jakarta.validation.constraints.NotNull;

public record BibleRefRequest(
	@NotNull Integer startBookId,
	@NotNull Integer startChapter,
	@NotNull Integer startVerse,
	@NotNull Integer endBookId,
	@NotNull Integer endChapter,
	@NotNull Integer endVerse
) {
}