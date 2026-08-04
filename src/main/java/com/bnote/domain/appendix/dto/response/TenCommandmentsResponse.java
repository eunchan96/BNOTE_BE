package com.bnote.domain.appendix.dto.response;

import java.util.List;

public record TenCommandmentsResponse(
	String title,
	List<String> intro,
	List<CommandmentItemResponse> commandments,
	String reference,
	CommandmentSummaryResponse summary
) {
}