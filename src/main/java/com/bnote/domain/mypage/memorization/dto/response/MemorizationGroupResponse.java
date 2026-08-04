package com.bnote.domain.mypage.memorization.dto.response;

import com.bnote.domain.mypage.memorization.entity.MemorizationGroup;

public record MemorizationGroupResponse(
	Long id,
	String name,
	int sortOrder
) {
	public static MemorizationGroupResponse from(MemorizationGroup group) {
		return new MemorizationGroupResponse(group.getId(), group.getName(), group.getSortOrder());
	}
}