package com.bnote.domain.mypage.copyformat.facade;

import com.bnote.domain.mypage.copyformat.dto.request.CopyFormatPresetRequest;
import com.bnote.domain.mypage.copyformat.dto.response.CopyFormatPresetResponse;
import com.bnote.domain.mypage.copyformat.service.CopyFormatPresetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CopyFormatPresetFacade {

	private final CopyFormatPresetService copyFormatPresetService;

	public List<CopyFormatPresetResponse> getAll(Long memberId) {
		return copyFormatPresetService.getAll(memberId);
	}

	public CopyFormatPresetResponse create(Long memberId, CopyFormatPresetRequest request) {
		return copyFormatPresetService.create(memberId, request);
	}

	public void delete(Long memberId, Long id) {
		copyFormatPresetService.delete(memberId, id);
	}
}