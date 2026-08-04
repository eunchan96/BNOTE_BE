package com.bnote.domain.sermon.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SermonCategoryRequest(
	@NotBlank String name,
	@NotBlank String colorHex,
	Integer sortOrder
) {
}