package com.bnote.domain.mypage.copyformat.service;

import com.bnote.domain.mypage.copyformat.dto.request.CopyFormatPresetRequest;
import com.bnote.domain.mypage.copyformat.dto.response.CopyFormatPresetResponse;
import com.bnote.domain.mypage.copyformat.entity.CopyFormatPreset;
import com.bnote.domain.mypage.copyformat.exception.CopyFormatPresetException;
import com.bnote.domain.mypage.copyformat.repository.CopyFormatPresetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CopyFormatPresetService {

	private final CopyFormatPresetRepository copyFormatPresetRepository;

	public List<CopyFormatPresetResponse> getAll(Long memberId) {
		return copyFormatPresetRepository.findByMemberIdOrderByCreateDateDesc(memberId)
			.stream()
			.map(CopyFormatPresetResponse::from)
			.toList();
	}

	@Transactional
	public CopyFormatPresetResponse create(Long memberId, CopyFormatPresetRequest request) {
		CopyFormatPreset preset = CopyFormatPreset.builder()
			.memberId(memberId)
			.name(request.name())
			.configJson(request.configJson())
			.build();
		return CopyFormatPresetResponse.from(copyFormatPresetRepository.save(preset));
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		CopyFormatPreset preset = copyFormatPresetRepository.findById(id)
			.orElseThrow(CopyFormatPresetException::notFound);

		if (!preset.getMemberId().equals(memberId)) {
			throw CopyFormatPresetException.accessDenied();
		}
		copyFormatPresetRepository.delete(preset);
	}
}