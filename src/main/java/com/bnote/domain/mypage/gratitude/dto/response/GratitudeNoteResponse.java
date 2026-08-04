package com.bnote.domain.mypage.gratitude.dto.response;

import com.bnote.domain.mypage.gratitude.entity.GratitudeEntry;
import com.bnote.domain.mypage.gratitude.entity.GratitudeNote;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record GratitudeNoteResponse(
	Long id,
	LocalDate date,
	List<EntryItem> entries,
	LocalDateTime createdAt
) {
	public record EntryItem(Long id, String text, int sortOrder) {
		public static EntryItem from(GratitudeEntry entry) {
			return new EntryItem(entry.getId(), entry.getText(), entry.getSortOrder());
		}
	}

	public static GratitudeNoteResponse of(GratitudeNote note, List<GratitudeEntry> entries) {
		return new GratitudeNoteResponse(
			note.getId(), note.getDate(), entries.stream().map(EntryItem::from).toList(), note.getCreateDate()
		);
	}
}