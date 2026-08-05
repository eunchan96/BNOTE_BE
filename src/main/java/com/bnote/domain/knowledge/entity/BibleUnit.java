package com.bnote.domain.knowledge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * "주제별 단원"이 아니라 성경에 나오는 도량형·단위(길이/무게/부피/화폐/시간 등)를 뜻한다.
 * 예) title="규빗", category="길이", summary="팔꿈치에서 손끝까지, 약 45cm"
 */
@Entity
@Getter
@Table(name = "bible_units")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BibleUnit {

	@Id
	private String id;

	/** 단위 이름 (예: 규빗, 세겔, 안식일 길이) */
	@Column(nullable = false)
	private String title;

	/** 종류 (예: 길이, 무게, 부피, 화폐, 시간) */
	private String category;

	@Column(nullable = false)
	private String summary;

	@Column(columnDefinition = "text")
	private String description;

	@Column(name = "key_book_id")
	private Integer keyBookId;

	@Column(name = "key_chapter")
	private Integer keyChapter;

	@Column(name = "key_verse_label")
	private String keyVerseLabel;

	@Builder
	private BibleUnit(
			String id, String title, String category, String summary, String description,
			Integer keyBookId, Integer keyChapter, String keyVerseLabel
	) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.summary = summary;
		this.description = description;
		this.keyBookId = keyBookId;
		this.keyChapter = keyChapter;
		this.keyVerseLabel = keyVerseLabel;
	}
}