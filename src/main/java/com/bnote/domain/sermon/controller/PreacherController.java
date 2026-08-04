package com.bnote.domain.sermon.controller;

import com.bnote.domain.sermon.dto.request.PreacherRequest;
import com.bnote.domain.sermon.dto.response.PreacherResponse;
import com.bnote.domain.sermon.facade.SermonFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PreacherController implements PreacherControllerDocs {

	private final SermonFacade sermonFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<PreacherResponse>> getAll() {
		return RsData.ok("설교자 목록 조회 성공", sermonFacade.getPreachers(rq.getActorIdOrThrow()));
	}

	@PostMapping
	public RsData<PreacherResponse> create(@Valid @RequestBody PreacherRequest request) {
		PreacherResponse response = sermonFacade.createPreacher(rq.getActorIdOrThrow(), request);
		return RsData.created("설교자 등록 성공", response);
	}

	@PutMapping("/{id}")
	public RsData<PreacherResponse> update(@PathVariable Long id, @Valid @RequestBody PreacherRequest request) {
		PreacherResponse response = sermonFacade.updatePreacher(rq.getActorIdOrThrow(), id, request);
		return RsData.ok("설교자 수정 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		sermonFacade.deletePreacher(rq.getActorIdOrThrow(), id);
		return RsData.ok("설교자 삭제 성공");
	}
}