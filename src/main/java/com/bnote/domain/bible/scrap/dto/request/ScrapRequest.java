package com.bnote.domain.bible.scrap.dto.request;

import jakarta.validation.constraints.NotNull;

public record ScrapRequest(
	@NotNull Long groupId,
	@NotNull Integer bookId,
	@NotNull Integer chapter,
	@NotNull Integer startVerse,
	@NotNull Integer endVerse,
	String translation
) {
}