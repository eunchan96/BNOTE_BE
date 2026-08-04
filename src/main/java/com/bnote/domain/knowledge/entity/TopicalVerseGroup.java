package com.bnote.domain.knowledge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "topical_verse_groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopicalVerseGroup {

	@Id
	private String id;

	@Column(nullable = false)
	private String title;

	@Builder
	private TopicalVerseGroup(String id, String title) {
		this.id = id;
		this.title = title;
	}
}