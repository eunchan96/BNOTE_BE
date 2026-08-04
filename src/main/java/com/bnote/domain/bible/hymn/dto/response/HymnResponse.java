package com.bnote.domain.bible.hymn.dto.response;

import com.bnote.domain.bible.hymn.entity.Hymn;
import java.util.Arrays;
import java.util.List;

public record HymnResponse(
	Integer number,
	String title,
	Long categoryId,
	List<String> imageFileNames,
	String youtubeSongUrl,
	String youtubeMrUrl
) {
	public static HymnResponse from(Hymn hymn) {
		return new HymnResponse(
			hymn.getNumber(),
			hymn.getTitle(),
			hymn.getCategoryId(),
			Arrays.asList(hymn.getImageFileName().split("\\|")),
			hymn.getYoutubeSongUrl(),
			hymn.getYoutubeMrUrl()
		);
	}
}