package com.bnote.domain.sermon.service;

import com.bnote.domain.sermon.dto.request.BibleRefRequest;
import com.bnote.domain.sermon.dto.request.SermonRequest;
import com.bnote.domain.sermon.dto.response.*;
import com.bnote.domain.sermon.entity.Sermon;
import com.bnote.domain.sermon.entity.SermonBibleRef;
import com.bnote.domain.sermon.entity.SermonPhoto;
import com.bnote.domain.sermon.exception.SermonException;
import com.bnote.domain.sermon.repository.*;
import com.bnote.global.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SermonService {

	private final SermonRepository sermonRepository;
	private final SermonBibleRefRepository sermonBibleRefRepository;
	private final SermonPhotoRepository sermonPhotoRepository;
	private final PreacherRepository preacherRepository;
	private final SermonCategoryRepository sermonCategoryRepository;
	private final PreacherService preacherService;
	private final SermonCategoryService sermonCategoryService;
	private final FileStorageService fileStorageService;

	public List<SermonResponse> getAll(Long memberId, String keyword) {
		List<Sermon> sermons = (keyword == null || keyword.isBlank())
			? sermonRepository.findByMemberIdOrderBySermonDateDesc(memberId)
			: sermonRepository.findByMemberIdAndTitleContainingOrderBySermonDateDesc(memberId, keyword.trim());

		return sermons.stream().map(this::toResponse).toList();
	}

	public SermonResponse getById(Long memberId, Long id) {
		return toResponse(findOwned(memberId, id));
	}

	@Transactional
	public SermonResponse create(Long memberId, SermonRequest request) {
		validateReferences(memberId, request.preacherId(), request.categoryId());

		Sermon sermon = sermonRepository.save(
			Sermon.builder()
				.memberId(memberId)
				.title(request.title())
				.preacherId(request.preacherId())
				.sermonDate(request.sermonDate())
				.categoryId(request.categoryId())
				.memo(request.memo() == null ? "" : request.memo())
				.link(request.link())
				.build()
		);

		saveBibleRefs(sermon.getId(), request.bibleRefs());

		return toResponse(sermon);
	}

	@Transactional
	public SermonResponse update(Long memberId, Long id, SermonRequest request) {
		Sermon sermon = findOwned(memberId, id);
		validateReferences(memberId, request.preacherId(), request.categoryId());

		sermon.update(
			request.title(), request.preacherId(), request.sermonDate(), request.categoryId(),
			request.memo() == null ? "" : request.memo(), request.link()
		);

		sermonBibleRefRepository.deleteBySermonId(sermon.getId());
		saveBibleRefs(sermon.getId(), request.bibleRefs());

		return toResponse(sermon);
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		Sermon sermon = findOwned(memberId, id);
		sermonBibleRefRepository.deleteBySermonId(sermon.getId());
		sermonPhotoRepository.deleteBySermonId(sermon.getId());
		sermonRepository.delete(sermon);
	}

	@Transactional
	public SermonPhotoResponse addPhoto(Long memberId, Long sermonId, MultipartFile file) {
		Sermon sermon = findOwned(memberId, sermonId);

		String imageUrl = fileStorageService.store(file, "sermons/" + sermon.getId());
		int nextOrder = sermonPhotoRepository.findBySermonIdOrderBySortOrderAsc(sermon.getId()).size();

		SermonPhoto photo = sermonPhotoRepository.save(
			SermonPhoto.builder().sermonId(sermon.getId()).imageUrl(imageUrl).sortOrder(nextOrder).build()
		);
		return SermonPhotoResponse.from(photo);
	}

	private void validateReferences(Long memberId, Long preacherId, Long categoryId) {
		if (preacherId != null) {
			preacherService.findOwned(memberId, preacherId);
		}
		if (categoryId != null) {
			sermonCategoryService.findOwned(memberId, categoryId);
		}
	}

	private void saveBibleRefs(Long sermonId, List<BibleRefRequest> bibleRefs) {
		if (bibleRefs == null) {
			return;
		}
		for (BibleRefRequest ref : bibleRefs) {
			sermonBibleRefRepository.save(
				SermonBibleRef.builder()
					.sermonId(sermonId)
					.startBookId(ref.startBookId())
					.startChapter(ref.startChapter())
					.startVerse(ref.startVerse())
					.endBookId(ref.endBookId())
					.endChapter(ref.endChapter())
					.endVerse(ref.endVerse())
					.build()
			);
		}
	}

	private Sermon findOwned(Long memberId, Long id) {
		Sermon sermon = sermonRepository.findById(id).orElseThrow(SermonException::sermonNotFound);
		if (!sermon.getMemberId().equals(memberId)) {
			throw SermonException.accessDenied();
		}
		return sermon;
	}

	private SermonResponse toResponse(Sermon sermon) {
		PreacherResponse preacher = sermon.getPreacherId() == null ? null
			: preacherRepository.findById(sermon.getPreacherId()).map(PreacherResponse::from).orElse(null);

		SermonCategoryResponse category = sermon.getCategoryId() == null ? null
			: sermonCategoryRepository.findById(sermon.getCategoryId()).map(SermonCategoryResponse::from).orElse(null);

		List<BibleRefResponse> bibleRefs = sermonBibleRefRepository.findBySermonId(sermon.getId())
			.stream().map(BibleRefResponse::from).toList();

		List<SermonPhotoResponse> photos = sermonPhotoRepository.findBySermonIdOrderBySortOrderAsc(sermon.getId())
			.stream().map(SermonPhotoResponse::from).toList();

		return SermonResponse.of(sermon, preacher, category, bibleRefs, photos);
	}
}