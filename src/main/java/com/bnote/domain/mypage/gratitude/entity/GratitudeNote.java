package com.bnote.domain.mypage.gratitude.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(
		name = "gratitude_notes",
		uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "note_date"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GratitudeNote extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	/** DB 컬럼명은 note_date — "date"도 예약어라 안전하게 명시 */
	@Column(name = "note_date", nullable = false)
	private LocalDate date;

	@Builder
	private GratitudeNote(Long memberId, LocalDate date) {
		this.memberId = memberId;
		this.date = date;
	}
}