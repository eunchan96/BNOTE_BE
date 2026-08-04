package com.bnote.domain.sermon.controller;

import com.bnote.domain.sermon.dto.request.SermonRequest;
import com.bnote.domain.sermon.dto.response.SermonPhotoResponse;
import com.bnote.domain.sermon.dto.response.SermonResponse;
import com.bnote.domain.sermon.facade.SermonFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SermonController implements SermonControllerDocs {

	private final SermonFacade sermonFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<SermonResponse>> getAll(@RequestParam(required = false) String keyword) {
		return RsData.ok("설교노트 목록 조회 성공", sermonFacade.getSermons(rq.getActorIdOrThrow(), keyword));
	}

	@GetMapping("/{id}")
	public RsData<SermonResponse> getById(@PathVariable Long id) {
		return RsData.ok("설교노트 상세 조회 성공", sermonFacade.getSermon(rq.getActorIdOrThrow(), id));
	}

	@PostMapping
	public RsData<SermonResponse> create(@Valid @RequestBody SermonRequest request) {
		SermonResponse response = sermonFacade.createSermon(rq.getActorIdOrThrow(), request);
		return RsData.created("설교노트 등록 성공", response);
	}

	@PutMapping("/{id}")
	public RsData<SermonResponse> update(@PathVariable Long id, @Valid @RequestBody SermonRequest request) {
		SermonResponse response = sermonFacade.updateSermon(rq.getActorIdOrThrow(), id, request);
		return RsData.ok("설교노트 수정 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		sermonFacade.deleteSermon(rq.getActorIdOrThrow(), id);
		return RsData.ok("설교노트 삭제 성공");
	}

	@PostMapping("/{id}/photos")
	public RsData<SermonPhotoResponse> addPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		SermonPhotoResponse response = sermonFacade.addPhoto(rq.getActorIdOrThrow(), id, file);
		return RsData.created("설교노트 사진 업로드 성공", response);
	}
}