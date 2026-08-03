package com.bnote.domain.partialhighlight.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
	name = "partial_highlights",
	indexes = @Index(name = "idx_partial_highlight_location", columnList = "member_id, book_id, chapter, verse")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartialHighlight extends BaseEntity {

	public static final String DEFAULT_COLOR_HEX = "#FFF9C4";

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

	/** 절이 소제목으로 쪼개진 경우 text(0)/text2(1) 구분 */
	@Column(nullable = false)
	private Integer segment;

	@Column(name = "color_hex", nullable = false, length = 10)
	private String colorHex;

	@Builder
	private PartialHighlight(
		Long memberId, String translation, Integer bookId, Integer chapter, Integer verse,
		Integer startOffset, Integer endOffset, Integer segment, String colorHex
	) {
		this.memberId = memberId;
		this.translation = translation;
		this.bookId = bookId;
		this.chapter = chapter;
		this.verse = verse;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
		this.segment = segment == null ? 0 : segment;
		this.colorHex = (colorHex == null || colorHex.isBlank()) ? DEFAULT_COLOR_HEX : colorHex;
	}
}