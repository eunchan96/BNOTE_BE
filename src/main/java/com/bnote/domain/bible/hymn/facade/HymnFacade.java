package com.bnote.domain.bible.hymn.facade;

import com.bnote.domain.bible.hymn.dto.response.HymnCategoryResponse;
import com.bnote.domain.bible.hymn.dto.response.HymnResponse;
import com.bnote.domain.bible.hymn.service.HymnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HymnFacade {

	private final HymnService hymnService;

	public List<HymnResponse> getAll(Long categoryId, String keyword) {
		return hymnService.getAll(categoryId, keyword);
	}

	public HymnResponse getByNumber(Integer number) {
		return hymnService.getByNumber(number);
	}

	public List<HymnCategoryResponse> getMajorCategories() {
		return hymnService.getMajorCategories();
	}

	public List<HymnCategoryResponse> getMinorCategories(Long majorId) {
		return hymnService.getMinorCategories(majorId);
	}
}