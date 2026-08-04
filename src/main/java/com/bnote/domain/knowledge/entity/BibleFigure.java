package com.bnote.domain.knowledge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 성경 배경지식 허브는 회원과 무관한 공용 참고 데이터라 CRUD API 없이 시더로만 채워진다.
 * id는 자동증가가 아니라 JSON에 정의된 문자열 slug(예: "abraham")를 그대로 쓴다.
 */
@Entity
@Getter
@Table(name = "bible_figures")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BibleFigure {

	@Id
	private String id;

	@Column(nullable = false)
	private String name;

	@Column(name = "other_names")
	private String otherNames;

	private String category;

	private String era;

	@Column(nullable = false)
	private String summary;

	@Lob
	private String description;

	@Column(name = "key_book_id")
	private Integer keyBookId;

	@Column(name = "key_chapter")
	private Integer keyChapter;

	@Column(name = "key_verse_label")
	private String keyVerseLabel;

	@Builder
	private BibleFigure(
		String id, String name, String otherNames, String category, String era, String summary,
		String description, Integer keyBookId, Integer keyChapter, String keyVerseLabel
	) {
		this.id = id;
		this.name = name;
		this.otherNames = otherNames;
		this.category = category;
		this.era = era;
		this.summary = summary;
		this.description = description;
		this.keyBookId = keyBookId;
		this.keyChapter = keyChapter;
		this.keyVerseLabel = keyVerseLabel;
	}
}