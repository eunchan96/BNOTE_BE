package com.bnote.domain.mypage.copyformat.entity;

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
@Table(name = "copy_format_presets")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CopyFormatPreset extends BaseEntity {

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(nullable = false)
	private String name;

	/** 복사 형식 옵션을 JSON 문자열로 통째로 저장 (구체적 스키마는 프론트에서 정의) */
	@Lob
	@Column(name = "config_json", nullable = false)
	private String configJson;

	@Builder
	private CopyFormatPreset(Long memberId, String name, String configJson) {
		this.memberId = memberId;
		this.name = name;
		this.configJson = configJson;
	}
}