package com.bnote.domain.application.service;

import com.bnote.domain.application.dto.request.ApplicationCategoryRequest;
import com.bnote.domain.application.dto.response.ApplicationCategoryResponse;
import com.bnote.domain.application.entity.ApplicationCategory;
import com.bnote.domain.application.exception.ApplicationException;
import com.bnote.domain.application.repository.ApplicationCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationCategoryService {

	private static final String[][] DEFAULT_CATEGORIES = {
		{"통독", "#4D96FF"},
		{"설교", "#FF6B6B"},
		{"교제", "#6BCB77"}
	};

	private final ApplicationCategoryRepository applicationCategoryRepository;

	public List<ApplicationCategoryResponse> getAll(Long memberId) {
		ensureDefaultCategories(memberId);
		return applicationCategoryRepository.findByMemberIdOrderBySortOrderAsc(memberId)
			.stream()
			.map(ApplicationCategoryResponse::from)
			.toList();
	}

	@Transactional
	public void ensureDefaultCategories(Long memberId) {
		if (applicationCategoryRepository.existsByMemberId(memberId)) {
			return;
		}
		for (int i = 0; i < DEFAULT_CATEGORIES.length; i++) {
			applicationCategoryRepository.save(
				ApplicationCategory.builder()
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
	public ApplicationCategoryResponse create(Long memberId, ApplicationCategoryRequest request) {
		ApplicationCategory category = ApplicationCategory.builder()
			.memberId(memberId)
			.name(request.name())
			.colorHex(request.colorHex())
			.isDefault(false)
			.sortOrder(request.sortOrder() == null ? 0 : request.sortOrder())
			.build();
		return ApplicationCategoryResponse.from(applicationCategoryRepository.save(category));
	}

	@Transactional
	public ApplicationCategoryResponse update(Long memberId, Long id, ApplicationCategoryRequest request) {
		ApplicationCategory category = findOwned(memberId, id);
		category.update(request.name(), request.colorHex(), request.sortOrder() == null ? category.getSortOrder() : request.sortOrder());
		return ApplicationCategoryResponse.from(category);
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		ApplicationCategory category = findOwned(memberId, id);
		if (category.isDefault()) {
			throw ApplicationException.defaultCategoryNotDeletable();
		}
		applicationCategoryRepository.delete(category);
	}

	ApplicationCategory findOwned(Long memberId, Long id) {
		ApplicationCategory category = applicationCategoryRepository.findById(id)
			.orElseThrow(ApplicationException::categoryNotFound);
		if (!category.getMemberId().equals(memberId)) {
			throw ApplicationException.accessDenied();
		}
		return category;
	}
}