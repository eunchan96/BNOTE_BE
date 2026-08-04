package com.bnote.domain.bible.bible.controller;

import com.bnote.domain.bible.bible.dto.response.BibleChapterResponse;
import com.bnote.domain.bible.bible.dto.response.BibleSearchResponse;
import com.bnote.domain.bible.bible.dto.response.TranslationResponse;
import com.bnote.domain.bible.bible.facade.BibleFacade;
import com.bnote.global.response.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BibleController implements BibleControllerDocs {

	private final BibleFacade bibleFacade;

	@GetMapping("/bibles/{bookId}/{chapter}")
	public RsData<BibleChapterResponse> getChapter(
			@PathVariable Integer bookId,
			@PathVariable Integer chapter,
			@RequestParam(required = false) String translation
	) {
		return RsData.ok("성경 조회 성공", bibleFacade.getChapter(bookId, chapter, translation));
	}

	@GetMapping("/bibles/search")
	public RsData<BibleSearchResponse> search(
			@RequestParam String keyword,
			@RequestParam(required = false) String translation
	) {
		return RsData.ok("성경 검색 성공", bibleFacade.search(keyword, translation));
	}

	@GetMapping("/translations")
	public RsData<TranslationResponse> getTranslations() {
		return RsData.ok("대역본 목록 조회 성공", bibleFacade.getTranslations());
	}
}