package com.bnote.domain.appendix.dto.response;

import java.util.List;

public record ResponsiveReadingResponse(
	int number,
	String title,
	List<ResponsiveReadingLineResponse> lines
) {
}