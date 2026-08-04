package com.bnote.domain.sermon.dto.response;

import com.bnote.domain.sermon.entity.Preacher;

public record PreacherResponse(
	Long id,
	String name,
	int sortOrder
) {
	public static PreacherResponse from(Preacher preacher) {
		return new PreacherResponse(preacher.getId(), preacher.getName(), preacher.getSortOrder());
	}
}