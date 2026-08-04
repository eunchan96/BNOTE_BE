package com.bnote.domain.bible.hymn.entity;

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
@Table(name = "hymns")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hymn {

	@Id
	private Integer number;

	@Column(nullable = false)
	private String title;

	@Column(name = "category_id", nullable = false)
	private Long categoryId;

	/** assets/hymns/images/ 아래 파일명. 여러 페이지면 "|"로 구분(예: "001_1.jpg|001_2.jpg") */
	@Column(name = "image_file_name", nullable = false)
	private String imageFileName;

	@Column(name = "youtube_song_url", nullable = false)
	private String youtubeSongUrl;

	@Column(name = "youtube_mr_url", nullable = false)
	private String youtubeMrUrl;

	@Builder
	private Hymn(Integer number, String title, Long categoryId, String imageFileName, String youtubeSongUrl, String youtubeMrUrl) {
		this.number = number;
		this.title = title;
		this.categoryId = categoryId;
		this.imageFileName = imageFileName;
		this.youtubeSongUrl = youtubeSongUrl;
		this.youtubeMrUrl = youtubeMrUrl;
	}
}