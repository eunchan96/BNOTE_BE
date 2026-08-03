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
	name = "verse_memos",
	indexes = @Index(name = "idx_verse_memo_location", columnList = "member_id, book_id, chapter, verse")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerseMemo extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "book_id", nullable = false)
	private Integer bookId;

	@Column(nullable = false)
	private Integer chapter;

	@Column(nullable = false)
	private Integer verse;

	@Lob
	@Column(nullable = false)
	private String text;

	@Builder
	private VerseMemo(Long memberId, Integer bookId, Integer chapter, Integer verse, String text) {
		this.memberId = memberId;
		this.bookId = bookId;
		this.chapter = chapter;
		this.verse = verse;
		this.text = text;
	}

	public void updateText(String text) {
		this.text = text;
	}
}