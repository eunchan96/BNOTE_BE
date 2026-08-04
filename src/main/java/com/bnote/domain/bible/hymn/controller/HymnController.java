package com.bnote.domain.bible.hymn.controller;

import com.bnote.domain.bible.hymn.dto.response.HymnCategoryResponse;
import com.bnote.domain.bible.hymn.dto.response.HymnResponse;
import com.bnote.domain.bible.hymn.facade.HymnFacade;
import com.bnote.global.response.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HymnController implements HymnControllerDocs {

	private final HymnFacade hymnFacade;

	@GetMapping("api/v1/hymns")
	public RsData<List<HymnResponse>> getAll(
		@RequestParam(required = false) Long categoryId,
		@RequestParam(required = false) String keyword
	) {
		return RsData.ok("찬송가 목록 조회 성공", hymnFacade.getAll(categoryId, keyword));
	}

	@GetMapping("api/v1/hymns/{number}")
	public RsData<HymnResponse> getByNumber(@PathVariable Integer number) {
		return RsData.ok("찬송가 상세 조회 성공", hymnFacade.getByNumber(number));
	}

	@GetMapping("api/v1/hymn-categories/major")
	public RsData<List<HymnCategoryResponse>> getMajorCategories() {
		return RsData.ok("대분류 카테고리 목록 조회 성공", hymnFacade.getMajorCategories());
	}

	@GetMapping("api/v1/hymn-categories/{majorId}/minor")
	public RsData<List<HymnCategoryResponse>> getMinorCategories(@PathVariable Long majorId) {
		return RsData.ok("소분류 카테고리 목록 조회 성공", hymnFacade.getMinorCategories(majorId));
	}
}