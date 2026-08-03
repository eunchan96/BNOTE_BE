package com.bnote.domain.bible.facade;

import com.bnote.domain.bible.dto.response.BibleChapterResponse;
import com.bnote.domain.bible.dto.response.BibleSearchResponse;
import com.bnote.domain.bible.dto.response.TranslationResponse;
import com.bnote.domain.bible.service.BibleService;
import org.springframework.stereotype.Service;

@Service
public class BibleFacade {

	private final BibleService bibleService;

	public BibleFacade(BibleService bibleService) {
		this.bibleService = bibleService;
	}

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