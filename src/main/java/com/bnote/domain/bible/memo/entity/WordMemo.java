package com.bnote.domain.bible.memo.entity;

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
	name = "word_memos",
	indexes = @Index(name = "idx_word_memo_location", columnList = "member_id, book_id, chapter, verse")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WordMemo extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(nullable = false, length = 20)
	private String translation;

	@Column(name = "book_id", nullable = false)
	private Integer bookId;

	@Column(nullable = false)
	private Integer chapter;

	@Column(nullable = false)
	private Integer verse;

	@Column(name = "start_offset", nullable = false)
	private Integer startOffset;

	@Column(name = "end_offset", nullable = false)
	private Integer endOffset;

	@Lob
	@Column(nullable = false)
	private String text;

	/** "다른 구절에도 추가"로 복사 생성된 경우, 원본 위치를 표기(예: "창 1:1") */
	@Column(name = "source_label")
	private String sourceLabel;

	@Builder
	private WordMemo(
		Long memberId, String translation, Integer bookId, Integer chapter, Integer verse,
		Integer startOffset, Integer endOffset, String text, String sourceLabel
	) {
		this.memberId = memberId;
		this.translation = translation;
		this.bookId = bookId;
		this.chapter = chapter;
		this.verse = verse;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
		this.text = text;
		this.sourceLabel = sourceLabel;
	}

	public void updateText(String text) {
		this.text = text;
	}
}