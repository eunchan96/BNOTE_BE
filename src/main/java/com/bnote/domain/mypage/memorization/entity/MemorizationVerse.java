package com.bnote.domain.mypage.memorization.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "memorization_verses")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemorizationVerse extends BaseEntity {

	@Column(name = "group_id", nullable = false)
	private Long groupId;

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

	private String note;

	@Column(name = "review_count", nullable = false)
	private int reviewCount;

	@Column(name = "last_reviewed_at")
	private LocalDateTime lastReviewedAt;

	@Column(name = "is_mastered", nullable = false)
	private boolean isMastered;

	@Builder
	private MemorizationVerse(
		Long groupId, Integer startBookId, Integer startChapter, Integer startVerse,
		Integer endBookId, Integer endChapter, Integer endVerse, String verseText, String note
	) {
		this.groupId = groupId;
		this.startBookId = startBookId;
		this.startChapter = startChapter;
		this.startVerse = startVerse;
		this.endBookId = endBookId;
		this.endChapter = endChapter;
		this.endVerse = endVerse;
		this.verseText = verseText;
		this.note = note;
	}

	/** 암송 연습 1회 완료 */
	public void recordReview(boolean mastered) {
		this.reviewCount++;
		this.lastReviewedAt = LocalDateTime.now();
		this.isMastered = mastered;
	}
}