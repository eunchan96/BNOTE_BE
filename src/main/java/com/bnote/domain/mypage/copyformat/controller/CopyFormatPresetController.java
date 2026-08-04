package com.bnote.domain.mypage.copyformat.controller;

import com.bnote.domain.mypage.copyformat.dto.request.CopyFormatPresetRequest;
import com.bnote.domain.mypage.copyformat.dto.response.CopyFormatPresetResponse;
import com.bnote.domain.mypage.copyformat.facade.CopyFormatPresetFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CopyFormatPresetController implements CopyFormatPresetControllerDocs {

	private final CopyFormatPresetFacade copyFormatPresetFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<CopyFormatPresetResponse>> getAll() {
		return RsData.ok("복사 형식 프리셋 목록 조회 성공", copyFormatPresetFacade.getAll(rq.getActorIdOrThrow()));
	}

	@PostMapping
	public RsData<CopyFormatPresetResponse> create(@Valid @RequestBody CopyFormatPresetRequest request) {
		CopyFormatPresetResponse response = copyFormatPresetFacade.create(rq.getActorIdOrThrow(), request);
		return RsData.created("복사 형식 프리셋 등록 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		copyFormatPresetFacade.delete(rq.getActorIdOrThrow(), id);
		return RsData.ok("복사 형식 프리셋 삭제 성공");
	}
}