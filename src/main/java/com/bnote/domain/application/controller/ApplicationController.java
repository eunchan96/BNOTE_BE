package com.bnote.domain.application.controller;

import com.bnote.domain.application.dto.request.ApplicationRequest;
import com.bnote.domain.application.dto.response.ApplicationResponse;
import com.bnote.domain.application.facade.ApplicationFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController implements ApplicationControllerDocs {

	private final ApplicationFacade applicationFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<ApplicationResponse>> getAll(@RequestParam(required = false) String keyword) {
		return RsData.ok("적용 목록 조회 성공", applicationFacade.getApplications(rq.getActorIdOrThrow(), keyword));
	}

	@GetMapping("/{id}")
	public RsData<ApplicationResponse> getById(@PathVariable Long id) {
		return RsData.ok("적용 상세 조회 성공", applicationFacade.getApplication(rq.getActorIdOrThrow(), id));
	}

	@PostMapping
	public RsData<ApplicationResponse> create(@Valid @RequestBody ApplicationRequest request) {
		ApplicationResponse response = applicationFacade.createApplication(rq.getActorIdOrThrow(), request);
		return RsData.created("적용 등록 성공", response);
	}

	@PutMapping("/{id}")
	public RsData<ApplicationResponse> update(@PathVariable Long id, @Valid @RequestBody ApplicationRequest request) {
		ApplicationResponse response = applicationFacade.updateApplication(rq.getActorIdOrThrow(), id, request);
		return RsData.ok("적용 수정 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		applicationFacade.deleteApplication(rq.getActorIdOrThrow(), id);
		return RsData.ok("적용 삭제 성공");
	}
}