package com.bnote.domain.bible.partialhighlight.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PartialHighlightRequest(
	@NotBlank String translation,
	@NotNull Integer bookId,
	@NotNull Integer chapter,
	@NotNull Integer verse,
	@NotNull Integer startOffset,
	@NotNull Integer endOffset,
	Integer segment,
	String colorHex
) {
}