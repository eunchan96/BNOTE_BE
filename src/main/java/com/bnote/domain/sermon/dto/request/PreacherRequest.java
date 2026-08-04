package com.bnote.domain.sermon.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PreacherRequest(
	@NotBlank String name,
	Integer sortOrder
) {
}