package com.bnote.domain.sermon.service;

import com.bnote.domain.sermon.dto.request.SermonCategoryRequest;
import com.bnote.domain.sermon.dto.response.SermonCategoryResponse;
import com.bnote.domain.sermon.entity.SermonCategory;
import com.bnote.domain.sermon.exception.SermonException;
import com.bnote.domain.sermon.repository.SermonCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SermonCategoryService {

	private static final String[][] DEFAULT_CATEGORIES = {
		{"주일예배", "#FF6B6B"},
		{"수요예배", "#4D96FF"},
		{"새벽기도", "#6BCB77"},
		{"특별집회", "#FFD93D"},
		{"성경공부", "#9D8DF1"},
		{"기타", "#B0B0B0"}
	};

	private final SermonCategoryRepository sermonCategoryRepository;

	public List<SermonCategoryResponse> getAll(Long memberId) {
		ensureDefaultCategories(memberId);
		return sermonCategoryRepository.findByMemberIdOrderBySortOrderAsc(memberId)
			.stream()
			.map(SermonCategoryResponse::from)
			.toList();
	}

	@Transactional
	public void ensureDefaultCategories(Long memberId) {
		if (sermonCategoryRepository.existsByMemberId(memberId)) {
			return;
		}
		for (int i = 0; i < DEFAULT_CATEGORIES.length; i++) {
			sermonCategoryRepository.save(
				SermonCategory.builder()
					.memberId(memberId)
					.name(DEFAULT_CATEGORIES[i][0])
					.colorHex(DEFAULT_CATEGORIES[i][1])
					.isDefault(true)
					.sortOrder(i)
					.build()
			);
		}
	}

	@Transactional
	public SermonCategoryResponse create(Long memberId, SermonCategoryRequest request) {
		SermonCategory category = SermonCategory.builder()
			.memberId(memberId)
			.name(request.name())
			.colorHex(request.colorHex())
			.isDefault(false)
			.sortOrder(request.sortOrder() == null ? 0 : request.sortOrder())
			.build();
		return SermonCategoryResponse.from(sermonCategoryRepository.save(category));
	}

	@Transactional
	public SermonCategoryResponse update(Long memberId, Long id, SermonCategoryRequest request) {
		SermonCategory category = findOwned(memberId, id);
		category.update(request.name(), request.colorHex(), request.sortOrder() == null ? category.getSortOrder() : request.sortOrder());
		return SermonCategoryResponse.from(category);
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		SermonCategory category = findOwned(memberId, id);
		if (category.isDefault()) {
			throw SermonException.defaultCategoryNotDeletable();
		}
		sermonCategoryRepository.delete(category);
	}

	SermonCategory findOwned(Long memberId, Long id) {
		SermonCategory category = sermonCategoryRepository.findById(id).orElseThrow(SermonException::categoryNotFound);
		if (!category.getMemberId().equals(memberId)) {
			throw SermonException.accessDenied();
		}
		return category;
	}
}