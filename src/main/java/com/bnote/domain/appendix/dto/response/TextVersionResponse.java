package com.bnote.domain.appendix.dto.response;

import java.util.List;

public record TextVersionResponse(
	String id,
	String label,
	List<String> lines
) {
}