package com.bnote.domain.knowledge.dto.response;

import com.bnote.domain.knowledge.entity.CultureTopic;

public record CultureTopicResponse(
	String id, String title, String category, String summary, String description,
	Integer keyBookId, Integer keyChapter, String keyVerseLabel
) {
	public static CultureTopicResponse from(CultureTopic e) {
		return new CultureTopicResponse(
			e.getId(), e.getTitle(), e.getCategory(), e.getSummary(), e.getDescription(),
			e.getKeyBookId(), e.getKeyChapter(), e.getKeyVerseLabel()
		);
	}
}