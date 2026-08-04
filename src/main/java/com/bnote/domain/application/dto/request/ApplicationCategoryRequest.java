package com.bnote.domain.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ApplicationCategoryRequest(
	@NotBlank String name,
	@NotBlank String colorHex,
	Integer sortOrder
) {
}