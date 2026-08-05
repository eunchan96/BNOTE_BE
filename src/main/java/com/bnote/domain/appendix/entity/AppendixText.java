package com.bnote.domain.appendix.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * lords-prayer / apostles-creed / ten-commandments 세 가지.
 * 구조가 타입마다 달라서(번역본 목록 vs 계명 목록+요약) 굳이 다 나눠서 테이블화하지 않고,
 * 원본 JSON 객체를 통째로 저장했다가 서비스에서 각 타입에 맞는 DTO로 다시 파싱한다.
 */
@Entity
@Getter
@Table(name = "appendix_texts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppendixText {

	@Id
	private String id;

	@Column(nullable = false)
	private String title;

	@Column(name = "content_json", nullable = false, columnDefinition = "text")
	private String contentJson;

	@Builder
	private AppendixText(String id, String title, String contentJson) {
		this.id = id;
		this.title = title;
		this.contentJson = contentJson;
	}
}