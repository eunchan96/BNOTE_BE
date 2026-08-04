package com.bnote.domain.knowledge.dto.response;

import com.bnote.domain.knowledge.entity.TimelineEvent;

public record TimelineEventResponse(
	String id, String era, String period, String title, String description,
	Integer keyBookId, Integer keyChapter, String keyVerseLabel
) {
	public static TimelineEventResponse from(TimelineEvent e) {
		return new TimelineEventResponse(
			e.getId(), e.getEra(), e.getPeriod(), e.getTitle(), e.getDescription(),
			e.getKeyBookId(), e.getKeyChapter(), e.getKeyVerseLabel()
		);
	}
}