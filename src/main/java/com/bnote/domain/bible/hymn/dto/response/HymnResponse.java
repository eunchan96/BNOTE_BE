package com.bnote.domain.bible.hymn.dto.response;

import com.bnote.domain.bible.hymn.entity.Hymn;
import java.util.Arrays;
import java.util.List;

public record HymnResponse(
		Integer number,
		String title,
		Long categoryId,
		List<String> imageUrls,
		String youtubeSongUrl,
		String youtubeMrUrl
) {
	private static final String IMAGE_URL_PREFIX = "/uploads/hymns/images/";

	public static HymnResponse from(Hymn hymn) {
		List<String> imageUrls = Arrays.stream(hymn.getImageFileName().split("\\|"))
				.map(fileName -> IMAGE_URL_PREFIX + fileName)
				.toList();

		return new HymnResponse(
				hymn.getNumber(),
				hymn.getTitle(),
				hymn.getCategoryId(),
				imageUrls,
				hymn.getYoutubeSongUrl(),
				hymn.getYoutubeMrUrl()
		);
	}
}