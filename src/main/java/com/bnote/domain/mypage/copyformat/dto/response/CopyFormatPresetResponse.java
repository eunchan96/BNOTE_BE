package com.bnote.domain.mypage.copyformat.dto.response;

import com.bnote.domain.mypage.copyformat.entity.CopyFormatPreset;
import java.time.LocalDateTime;

public record CopyFormatPresetResponse(
	Long id,
	String name,
	String configJson,
	LocalDateTime createdAt
) {
	public static CopyFormatPresetResponse from(CopyFormatPreset preset) {
		return new CopyFormatPresetResponse(preset.getId(), preset.getName(), preset.getConfigJson(), preset.getCreateDate());
	}
}