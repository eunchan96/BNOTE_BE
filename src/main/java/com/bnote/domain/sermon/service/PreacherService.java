package com.bnote.domain.sermon.service;

import com.bnote.domain.sermon.dto.request.PreacherRequest;
import com.bnote.domain.sermon.dto.response.PreacherResponse;
import com.bnote.domain.sermon.entity.Preacher;
import com.bnote.domain.sermon.exception.SermonException;
import com.bnote.domain.sermon.repository.PreacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PreacherService {

	private final PreacherRepository preacherRepository;

	public List<PreacherResponse> getAll(Long memberId) {
		return preacherRepository.findByMemberIdOrderBySortOrderAsc(memberId)
				.stream()
				.map(PreacherResponse::from)
				.toList();
	}

	@Transactional
	public PreacherResponse create(Long memberId, PreacherRequest request) {
		Preacher preacher = Preacher.builder()
				.memberId(memberId)
				.name(request.name())
				.sortOrder(request.sortOrder() == null ? 0 : request.sortOrder())
				.build();
		return PreacherResponse.from(preacherRepository.save(preacher));
	}

	@Transactional
	public PreacherResponse update(Long memberId, Long id, PreacherRequest request) {
		Preacher preacher = findOwned(memberId, id);
		preacher.update(request.name(), request.sortOrder() == null ? preacher.getSortOrder() : request.sortOrder());
		return PreacherResponse.from(preacher);
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		preacherRepository.delete(findOwned(memberId, id));
	}

	Preacher findOwned(Long memberId, Long id) {
		Preacher preacher = preacherRepository.findById(id).orElseThrow(SermonException::preacherNotFound);
		if (!preacher.getMemberId().equals(memberId)) {
			throw SermonException.accessDenied();
		}
		return preacher;
	}
}