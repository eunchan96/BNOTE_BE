package com.bnote.domain.mypage.copyformat.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CopyFormatPresetRequest(
	@NotBlank String name,
	@NotBlank String configJson
) {
}