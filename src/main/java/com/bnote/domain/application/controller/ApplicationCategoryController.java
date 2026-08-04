package com.bnote.domain.application.controller;

import com.bnote.domain.application.dto.request.ApplicationCategoryRequest;
import com.bnote.domain.application.dto.response.ApplicationCategoryResponse;
import com.bnote.domain.application.facade.ApplicationFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationCategoryController implements ApplicationCategoryControllerDocs {

	private final ApplicationFacade applicationFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<ApplicationCategoryResponse>> getAll() {
		return RsData.ok("적용 카테고리 목록 조회 성공", applicationFacade.getCategories(rq.getActorIdOrThrow()));
	}

	@PostMapping
	public RsData<ApplicationCategoryResponse> create(@Valid @RequestBody ApplicationCategoryRequest request) {
		ApplicationCategoryResponse response = applicationFacade.createCategory(rq.getActorIdOrThrow(), request);
		return RsData.created("적용 카테고리 등록 성공", response);
	}

	@PutMapping("/{id}")
	public RsData<ApplicationCategoryResponse> update(@PathVariable Long id, @Valid @RequestBody ApplicationCategoryRequest request) {
		ApplicationCategoryResponse response = applicationFacade.updateCategory(rq.getActorIdOrThrow(), id, request);
		return RsData.ok("적용 카테고리 수정 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		applicationFacade.deleteCategory(rq.getActorIdOrThrow(), id);
		return RsData.ok("적용 카테고리 삭제 성공");
	}
}