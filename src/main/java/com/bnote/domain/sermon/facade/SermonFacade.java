package com.bnote.domain.sermon.facade;

import com.bnote.domain.sermon.dto.request.PreacherRequest;
import com.bnote.domain.sermon.dto.request.SermonCategoryRequest;
import com.bnote.domain.sermon.dto.request.SermonRequest;
import com.bnote.domain.sermon.dto.response.PreacherResponse;
import com.bnote.domain.sermon.dto.response.SermonCategoryResponse;
import com.bnote.domain.sermon.dto.response.SermonPhotoResponse;
import com.bnote.domain.sermon.dto.response.SermonResponse;
import com.bnote.domain.sermon.service.PreacherService;
import com.bnote.domain.sermon.service.SermonCategoryService;
import com.bnote.domain.sermon.service.SermonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SermonFacade {

	private final SermonService sermonService;
	private final PreacherService preacherService;
	private final SermonCategoryService sermonCategoryService;

	public List<SermonResponse> getSermons(Long memberId, String keyword) {
		return sermonService.getAll(memberId, keyword);
	}

	public SermonResponse getSermon(Long memberId, Long id) {
		return sermonService.getById(memberId, id);
	}

	public SermonResponse createSermon(Long memberId, SermonRequest request) {
		return sermonService.create(memberId, request);
	}

	public SermonResponse updateSermon(Long memberId, Long id, SermonRequest request) {
		return sermonService.update(memberId, id, request);
	}

	public void deleteSermon(Long memberId, Long id) {
		sermonService.delete(memberId, id);
	}

	public SermonPhotoResponse addPhoto(Long memberId, Long sermonId, MultipartFile file) {
		return sermonService.addPhoto(memberId, sermonId, file);
	}

	public List<PreacherResponse> getPreachers(Long memberId) {
		return preacherService.getAll(memberId);
	}

	public PreacherResponse createPreacher(Long memberId, PreacherRequest request) {
		return preacherService.create(memberId, request);
	}

	public PreacherResponse updatePreacher(Long memberId, Long id, PreacherRequest request) {
		return preacherService.update(memberId, id, request);
	}

	public void deletePreacher(Long memberId, Long id) {
		preacherService.delete(memberId, id);
	}

	public List<SermonCategoryResponse> getCategories(Long memberId) {
		return sermonCategoryService.getAll(memberId);
	}

	public SermonCategoryResponse createCategory(Long memberId, SermonCategoryRequest request) {
		return sermonCategoryService.create(memberId, request);
	}

	public SermonCategoryResponse updateCategory(Long memberId, Long id, SermonCategoryRequest request) {
		return sermonCategoryService.update(memberId, id, request);
	}

	public void deleteCategory(Long memberId, Long id) {
		sermonCategoryService.delete(memberId, id);
	}
}