package com.bnote.domain.bible.bible.facade;

import com.bnote.domain.bible.bible.dto.response.BibleChapterResponse;
import com.bnote.domain.bible.bible.dto.response.BibleSearchResponse;
import com.bnote.domain.bible.bible.dto.response.TranslationResponse;
import com.bnote.domain.bible.bible.service.BibleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BibleFacade {

	private final BibleService bibleService;

	public BibleChapterResponse getChapter(Integer bookId, Integer chapter, String translation) {
		return bibleService.getChapter(bookId, chapter, translation);
	}

	public BibleSearchResponse search(String keyword, String translation) {
		return bibleService.search(keyword, translation);
	}

	public TranslationResponse getTranslations() {
		return TranslationResponse.ofAll();
	}
}