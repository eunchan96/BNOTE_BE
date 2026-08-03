package com.bnote.domain.bible.dto.response;

import com.bnote.domain.bible.entity.Translation;
import java.util.Arrays;
import java.util.List;

public record TranslationResponse(
	List<Item> translations
) {
	public record Item(String code, String name) {
	}

	public static TranslationResponse ofAll() {
		return new TranslationResponse(
			Arrays.stream(Translation.values())
				.map(t -> new Item(t.getCode(), t.getDisplayName()))
				.toList()
		);
	}
}