package com.bnote.domain.sermon.controller;

import com.bnote.domain.sermon.dto.request.SermonCategoryRequest;
import com.bnote.domain.sermon.dto.response.SermonCategoryResponse;
import com.bnote.domain.sermon.facade.SermonFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SermonCategoryController implements SermonCategoryControllerDocs {

	private final SermonFacade sermonFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<SermonCategoryResponse>> getAll() {
		return RsData.ok("설교 카테고리 목록 조회 성공", sermonFacade.getCategories(rq.getActorIdOrThrow()));
	}

	@PostMapping
	public RsData<SermonCategoryResponse> create(@Valid @RequestBody SermonCategoryRequest request) {
		SermonCategoryResponse response = sermonFacade.createCategory(rq.getActorIdOrThrow(), request);
		return RsData.created("설교 카테고리 등록 성공", response);
	}

	@PutMapping("/{id}")
	public RsData<SermonCategoryResponse> update(@PathVariable Long id, @Valid @RequestBody SermonCategoryRequest request) {
		SermonCategoryResponse response = sermonFacade.updateCategory(rq.getActorIdOrThrow(), id, request);
		return RsData.ok("설교 카테고리 수정 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		sermonFacade.deleteCategory(rq.getActorIdOrThrow(), id);
		return RsData.ok("설교 카테고리 삭제 성공");
	}
}