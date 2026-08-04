package com.bnote.domain.appendix.dto.response;

import java.util.List;

public record VersionedTextResponse(
	String title,
	List<TextVersionResponse> versions
) {
}