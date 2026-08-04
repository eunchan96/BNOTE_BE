package com.bnote.domain.mypage.verseofyear.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
	name = "verse_of_years",
	uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "year"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VerseOfYear extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(nullable = false)
	private Integer year;

	private String note;

	@Builder
	private VerseOfYear(Long memberId, Integer year, String note) {
		this.memberId = memberId;
		this.year = year;
		this.note = note;
	}

	public void updateNote(String note) {
		this.note = note;
	}
}