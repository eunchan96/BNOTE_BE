package com.bnote.domain.sermon.dto.response;

import com.bnote.domain.sermon.entity.SermonPhoto;

public record SermonPhotoResponse(
	Long id,
	String imageUrl,
	int sortOrder
) {
	public static SermonPhotoResponse from(SermonPhoto photo) {
		return new SermonPhotoResponse(photo.getId(), photo.getImageUrl(), photo.getSortOrder());
	}
}