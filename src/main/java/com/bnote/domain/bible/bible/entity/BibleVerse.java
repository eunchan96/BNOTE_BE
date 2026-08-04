package com.bnote.domain.bible.bible.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
	name = "bible_verses",
	indexes = @Index(name = "idx_bible_verse_location", columnList = "translation, bookId, chapter, verse")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BibleVerse extends BaseEntity {

	@Column(nullable = false, length = 20)
	private String translation;

	@Column(nullable = false)
	private Integer bookId;

	@Column(nullable = false)
	private Integer chapter;

	@Column(nullable = false)
	private Integer verse;

	@Lob
	@Column(nullable = false)
	private String text;

	private String title;

	/**
	 * 절 중간에 소제목이 끼어드는 드문 경우(예: 창 35:22)에만 사용.
	 * title2가 있으면 text 다음에 소제목처럼 표시되고, 이어서 text2가 나온다.
	 */
	private String title2;

	@Lob
	private String text2;

	@Builder
	private BibleVerse(
		String translation, Integer bookId, Integer chapter, Integer verse,
		String text, String title, String title2, String text2
	) {
		this.translation = translation;
		this.bookId = bookId;
		this.chapter = chapter;
		this.verse = verse;
		this.text = text;
		this.title = title;
		this.title2 = title2;
		this.text2 = text2;
	}
}