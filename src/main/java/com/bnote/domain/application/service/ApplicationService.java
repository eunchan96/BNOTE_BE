package com.bnote.domain.application.service;

import com.bnote.domain.application.dto.request.ApplicationBibleRefRequest;
import com.bnote.domain.application.dto.request.ApplicationRequest;
import com.bnote.domain.application.dto.response.ApplicationBibleRefResponse;
import com.bnote.domain.application.dto.response.ApplicationCategoryResponse;
import com.bnote.domain.application.dto.response.ApplicationResponse;
import com.bnote.domain.application.entity.Application;
import com.bnote.domain.application.entity.ApplicationBibleRef;
import com.bnote.domain.application.entity.ApplicationSermonLink;
import com.bnote.domain.application.exception.ApplicationException;
import com.bnote.domain.application.repository.ApplicationBibleRefRepository;
import com.bnote.domain.application.repository.ApplicationCategoryRepository;
import com.bnote.domain.application.repository.ApplicationRepository;
import com.bnote.domain.application.repository.ApplicationSermonLinkRepository;
import com.bnote.domain.sermon.repository.SermonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

	private final ApplicationRepository applicationRepository;
	private final ApplicationBibleRefRepository applicationBibleRefRepository;
	private final ApplicationSermonLinkRepository applicationSermonLinkRepository;
	private final ApplicationCategoryRepository applicationCategoryRepository;
	private final ApplicationCategoryService applicationCategoryService;
	private final SermonRepository sermonRepository;

	public List<ApplicationResponse> getAll(Long memberId, String keyword) {
		List<Application> applications = (keyword == null || keyword.isBlank())
			? applicationRepository.findByMemberIdOrderByApplicationDateDesc(memberId)
			: applicationRepository.findByMemberIdAndTitleContainingOrderByApplicationDateDesc(memberId, keyword.trim());

		return applications.stream().map(this::toResponse).toList();
	}

	public ApplicationResponse getById(Long memberId, Long id) {
		return toResponse(findOwned(memberId, id));
	}

	@Transactional
	public ApplicationResponse create(Long memberId, ApplicationRequest request) {
		validateReferences(memberId, request.categoryId(), request.sermonIds());

		Application application = applicationRepository.save(
			Application.builder()
				.memberId(memberId)
				.title(request.title())
				.categoryId(request.categoryId())
				.applicationDate(request.applicationDate())
				.meditationMemo(request.meditationMemo())
				.prayerMemo(request.prayerMemo())
				.obedienceMemo(request.obedienceMemo())
				.build()
		);

		saveBibleRefs(application.getId(), request.bibleRefs());
		saveSermonLinks(application.getId(), request.sermonIds());

		return toResponse(application);
	}

	@Transactional
	public ApplicationResponse update(Long memberId, Long id, ApplicationRequest request) {
		Application application = findOwned(memberId, id);
		validateReferences(memberId, request.categoryId(), request.sermonIds());

		application.update(
			request.title(), request.categoryId(), request.applicationDate(),
			request.meditationMemo(), request.prayerMemo(), request.obedienceMemo()
		);

		applicationBibleRefRepository.deleteByApplicationId(application.getId());
		saveBibleRefs(application.getId(), request.bibleRefs());

		applicationSermonLinkRepository.deleteByApplicationId(application.getId());
		saveSermonLinks(application.getId(), request.sermonIds());

		return toResponse(application);
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		Application application = findOwned(memberId, id);
		applicationBibleRefRepository.deleteByApplicationId(application.getId());
		applicationSermonLinkRepository.deleteByApplicationId(application.getId());
		applicationRepository.delete(application);
	}

	private void validateReferences(Long memberId, Long categoryId, List<Long> sermonIds) {
		if (categoryId != null) {
			applicationCategoryService.findOwned(memberId, categoryId);
		}
		if (sermonIds != null) {
			for (Long sermonId : sermonIds) {
				var sermon = sermonRepository.findById(sermonId).orElseThrow(ApplicationException::sermonNotFound);
				if (!sermon.getMemberId().equals(memberId)) {
					throw ApplicationException.accessDenied();
				}
			}
		}
	}

	private void saveBibleRefs(Long applicationId, List<ApplicationBibleRefRequest> bibleRefs) {
		if (bibleRefs == null) {
			return;
		}
		for (ApplicationBibleRefRequest ref : bibleRefs) {
			applicationBibleRefRepository.save(
				ApplicationBibleRef.builder()
					.applicationId(applicationId)
					.startBookId(ref.startBookId())
					.startChapter(ref.startChapter())
					.startVerse(ref.startVerse())
					.endBookId(ref.endBookId())
					.endChapter(ref.endChapter())
					.endVerse(ref.endVerse())
					.isChapterOnly(ref.isChapterOnly())
					.build()
			);
		}
	}

	private void saveSermonLinks(Long applicationId, List<Long> sermonIds) {
		if (sermonIds == null) {
			return;
		}
		for (Long sermonId : sermonIds) {
			applicationSermonLinkRepository.save(
				ApplicationSermonLink.builder().applicationId(applicationId).sermonId(sermonId).build()
			);
		}
	}

	private Application findOwned(Long memberId, Long id) {
		Application application = applicationRepository.findById(id).orElseThrow(ApplicationException::applicationNotFound);
		if (!application.getMemberId().equals(memberId)) {
			throw ApplicationException.accessDenied();
		}
		return application;
	}

	private ApplicationResponse toResponse(Application application) {
		ApplicationCategoryResponse category = application.getCategoryId() == null ? null
			: applicationCategoryRepository.findById(application.getCategoryId())
				.map(ApplicationCategoryResponse::from).orElse(null);

		List<ApplicationBibleRefResponse> bibleRefs = applicationBibleRefRepository.findByApplicationId(application.getId())
			.stream().map(ApplicationBibleRefResponse::from).toList();

		List<Long> sermonIds = applicationSermonLinkRepository.findByApplicationId(application.getId())
			.stream().map(ApplicationSermonLink::getSermonId).toList();

		return ApplicationResponse.of(application, category, bibleRefs, sermonIds);
	}
}