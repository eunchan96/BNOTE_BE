package com.bnote.domain.bible.scrap.entity;

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
@Table(name = "scraps")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap extends BaseEntity {

	@Column(name = "group_id", nullable = false)
	private Long groupId;

	@Column(name = "book_id", nullable = false)
	private Integer bookId;

	@Column(nullable = false)
	private Integer chapter;

	@Column(name = "start_verse", nullable = false)
	private Integer startVerse;

	@Column(name = "end_verse", nullable = false)
	private Integer endVerse;

	/** 스크랩 당시 본문. 이후 번역본/원문이 바뀌어도 스크랩한 순간의 텍스트를 그대로 보존한다. */
	@Lob
	@Column(name = "verse_text", nullable = false)
	private String verseText;

	@Builder
	private Scrap(Long groupId, Integer bookId, Integer chapter, Integer startVerse, Integer endVerse, String verseText) {
		this.groupId = groupId;
		this.bookId = bookId;
		this.chapter = chapter;
		this.startVerse = startVerse;
		this.endVerse = endVerse;
		this.verseText = verseText;
	}
}