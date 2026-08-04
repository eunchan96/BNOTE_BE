package com.bnote.domain.application.facade;

import com.bnote.domain.application.dto.request.ApplicationCategoryRequest;
import com.bnote.domain.application.dto.request.ApplicationRequest;
import com.bnote.domain.application.dto.response.ApplicationCategoryResponse;
import com.bnote.domain.application.dto.response.ApplicationResponse;
import com.bnote.domain.application.service.ApplicationCategoryService;
import com.bnote.domain.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationFacade {

	private final ApplicationService applicationService;
	private final ApplicationCategoryService applicationCategoryService;

	public List<ApplicationResponse> getApplications(Long memberId, String keyword) {
		return applicationService.getAll(memberId, keyword);
	}

	public ApplicationResponse getApplication(Long memberId, Long id) {
		return applicationService.getById(memberId, id);
	}

	public ApplicationResponse createApplication(Long memberId, ApplicationRequest request) {
		return applicationService.create(memberId, request);
	}

	public ApplicationResponse updateApplication(Long memberId, Long id, ApplicationRequest request) {
		return applicationService.update(memberId, id, request);
	}

	public void deleteApplication(Long memberId, Long id) {
		applicationService.delete(memberId, id);
	}

	public List<ApplicationCategoryResponse> getCategories(Long memberId) {
		return applicationCategoryService.getAll(memberId);
	}

	public ApplicationCategoryResponse createCategory(Long memberId, ApplicationCategoryRequest request) {
		return applicationCategoryService.create(memberId, request);
	}

	public ApplicationCategoryResponse updateCategory(Long memberId, Long id, ApplicationCategoryRequest request) {
		return applicationCategoryService.update(memberId, id, request);
	}

	public void deleteCategory(Long memberId, Long id) {
		applicationCategoryService.delete(memberId, id);
	}
}