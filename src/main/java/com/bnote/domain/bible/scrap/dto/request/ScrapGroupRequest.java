package com.bnote.domain.bible.scrap.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ScrapGroupRequest(
	@NotBlank String name,
	Integer sortOrder
) {
}