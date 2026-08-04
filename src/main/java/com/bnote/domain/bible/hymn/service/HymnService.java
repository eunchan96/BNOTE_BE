package com.bnote.domain.bible.hymn.service;

import com.bnote.domain.bible.hymn.dto.response.HymnCategoryResponse;
import com.bnote.domain.bible.hymn.dto.response.HymnResponse;
import com.bnote.domain.bible.hymn.entity.Hymn;
import com.bnote.domain.bible.hymn.exception.HymnException;
import com.bnote.domain.bible.hymn.repository.HymnCategoryRepository;
import com.bnote.domain.bible.hymn.repository.HymnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HymnService {

	private final HymnRepository hymnRepository;
	private final HymnCategoryRepository hymnCategoryRepository;

	public List<HymnResponse> getAll(Long categoryId, String keyword) {
		List<Hymn> hymns;
		if (keyword != null && !keyword.isBlank()) {
			hymns = hymnRepository.findByTitleContainingOrderByNumberAsc(keyword.trim());
		} else if (categoryId != null) {
			hymns = hymnRepository.findByCategoryIdOrderByNumberAsc(categoryId);
		} else {
			hymns = hymnRepository.findAllByOrderByNumberAsc();
		}
		return hymns.stream().map(HymnResponse::from).toList();
	}

	public HymnResponse getByNumber(Integer number) {
		Hymn hymn = hymnRepository.findById(number).orElseThrow(HymnException::notFound);
		return HymnResponse.from(hymn);
	}

	public List<HymnCategoryResponse> getMajorCategories() {
		return hymnCategoryRepository.findByParentIdIsNullOrderBySortOrderAsc()
			.stream()
			.map(HymnCategoryResponse::from)
			.toList();
	}

	public List<HymnCategoryResponse> getMinorCategories(Long majorId) {
		return hymnCategoryRepository.findByParentIdOrderBySortOrderAsc(majorId)
			.stream()
			.map(HymnCategoryResponse::from)
			.toList();
	}
}