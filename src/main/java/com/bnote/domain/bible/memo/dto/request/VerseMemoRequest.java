package com.bnote.domain.bible.memo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerseMemoRequest(
	@NotNull Integer bookId,
	@NotNull Integer chapter,
	@NotNull Integer verse,
	@NotBlank String text
) {
}