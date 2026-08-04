package com.bnote.domain.application.dto.response;

import com.bnote.domain.application.entity.ApplicationCategory;

public record ApplicationCategoryResponse(
	Long id,
	String name,
	String colorHex,
	boolean isDefault,
	int sortOrder
) {
	public static ApplicationCategoryResponse from(ApplicationCategory category) {
		return new ApplicationCategoryResponse(
			category.getId(), category.getName(), category.getColorHex(), category.isDefault(), category.getSortOrder()
		);
	}
}