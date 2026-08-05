package com.bnote.domain.appendix.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "responsive_readings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponsiveReadingEntity {

	@Id
	private Integer number;

	@Column(nullable = false)
	private String title;

	/** [{ "speaker": "leader|congregation|unison", "text": "..." }] 형태의 JSON 배열 원본 */
	@Column(name = "lines_json", nullable = false, columnDefinition = "text")
	private String linesJson;

	@Builder
	private ResponsiveReadingEntity(Integer number, String title, String linesJson) {
		this.number = number;
		this.title = title;
		this.linesJson = linesJson;
	}
}