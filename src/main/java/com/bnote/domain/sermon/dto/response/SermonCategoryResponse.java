package com.bnote.domain.sermon.dto.response;

import com.bnote.domain.sermon.entity.SermonCategory;

public record SermonCategoryResponse(
	Long id,
	String name,
	String colorHex,
	boolean isDefault,
	int sortOrder
) {
	public static SermonCategoryResponse from(SermonCategory category) {
		return new SermonCategoryResponse(
			category.getId(), category.getName(), category.getColorHex(), category.isDefault(), category.getSortOrder()
		);
	}
}