package com.bnote.domain.mypage.gratitude.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "gratitude_entries")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GratitudeEntry extends BaseEntity {

	@Column(name = "note_id", nullable = false)
	private Long noteId;

	@Column(nullable = false)
	private String text;

	@Column(name = "sort_order", nullable = false)
	private int sortOrder;

	@Builder
	private GratitudeEntry(Long noteId, String text, int sortOrder) {
		this.noteId = noteId;
		this.text = text;
		this.sortOrder = sortOrder;
	}
}