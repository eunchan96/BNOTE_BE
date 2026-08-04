package com.bnote.domain.bible.memo.dto.request;

import jakarta.validation.constraints.NotBlank;

public record VerseMemoUpdateRequest(
	@NotBlank String text
) {
}