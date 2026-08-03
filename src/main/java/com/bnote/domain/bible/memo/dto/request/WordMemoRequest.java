package com.bnote.domain.bible.memo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WordMemoRequest(
	@NotBlank String translation,
	@NotNull Integer bookId,
	@NotNull Integer chapter,
	@NotNull Integer verse,
	@NotNull Integer startOffset,
	@NotNull Integer endOffset,
	@NotBlank String text
) {
}