package com.bnote.domain.mypage.memorization.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemorizationGroupRequest(
	@NotBlank String name,
	Integer sortOrder
) {
}