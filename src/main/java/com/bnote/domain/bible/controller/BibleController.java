package com.bnote.domain.bible.controller;

import com.bnote.domain.bible.dto.response.BibleChapterResponse;
import com.bnote.domain.bible.dto.response.BibleSearchResponse;
import com.bnote.domain.bible.dto.response.TranslationResponse;
import com.bnote.domain.bible.service.BibleService;
import com.bnote.global.rsData.RsData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BibleController {

	private final BibleService bibleService;

	public BibleController(BibleService bibleService) {
		this.bibleService = bibleService;
	}

	@GetMapping("/bibles/{bookId}/{chapter}")
	public RsData<BibleChapterResponse> getChapter(
		@PathVariable Integer bookId,
		@PathVariable Integer chapter,
		@RequestParam(required = false) String translation
	) {
		return new RsData<>(
			"200-1", "성경 조회 성공", bibleService.getChapter(bookId, chapter, translation)
		);
	}

	@GetMapping("/bibles/search")
	public RsData<BibleSearchResponse> search(
		@RequestParam String keyword,
		@RequestParam(required = false) String translation
	) {
		return new RsData<>(
			"200-1", "성경 검색 성공", bibleService.search(keyword, translation)
		);
	}

	@GetMapping("/translations")
	public RsData<TranslationResponse> getTranslations() {
		return new RsData<>("200-1", "대역본 목록 조회 성공", TranslationResponse.ofAll());
	}
}