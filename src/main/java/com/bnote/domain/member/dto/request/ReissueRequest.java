package com.bnote.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ReissueRequest(
	@NotBlank
	String refreshToken
) {
}