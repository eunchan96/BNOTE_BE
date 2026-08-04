package com.bnote.domain.mypage.verseofyear.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "verse_of_year_refs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerseOfYearRef extends BaseEntity {

	@Column(name = "verse_of_year_id", nullable = false)
	private Long verseOfYearId;

	@Column(name = "start_book_id", nullable = false)
	private Integer startBookId;

	@Column(name = "start_chapter", nullable = false)
	private Integer startChapter;

	@Column(name = "start_verse", nullable = false)
	private Integer startVerse;

	@Column(name = "end_book_id", nullable = false)
	private Integer endBookId;

	@Column(name = "end_chapter", nullable = false)
	private Integer endChapter;

	@Column(name = "end_verse", nullable = false)
	private Integer endVerse;

	@Lob
	@Column(name = "verse_text", nullable = false)
	private String verseText;

	@Builder
	private VerseOfYearRef(
		Long verseOfYearId, Integer startBookId, Integer startChapter, Integer startVerse,
		Integer endBookId, Integer endChapter, Integer endVerse, String verseText
	) {
		this.verseOfYearId = verseOfYearId;
		this.startBookId = startBookId;
		this.startChapter = startChapter;
		this.startVerse = startVerse;
		this.endBookId = endBookId;
		this.endChapter = endChapter;
		this.endVerse = endVerse;
		this.verseText = verseText;
	}
}