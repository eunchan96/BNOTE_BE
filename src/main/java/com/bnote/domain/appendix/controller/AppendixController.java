package com.bnote.domain.appendix.controller;

import com.bnote.domain.appendix.dto.response.ResponsiveReadingResponse;
import com.bnote.domain.appendix.dto.response.TenCommandmentsResponse;
import com.bnote.domain.appendix.dto.response.VersionedTextResponse;
import com.bnote.domain.appendix.facade.AppendixFacade;
import com.bnote.global.response.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppendixController implements AppendixControllerDocs {

	private final AppendixFacade appendixFacade;

	@GetMapping("api/v1/appendix/lords-prayer")
	public RsData<VersionedTextResponse> getLordsPrayer() {
		return RsData.ok("주기도문 조회 성공", appendixFacade.getLordsPrayer());
	}

	@GetMapping("api/v1/appendix/apostles-creed")
	public RsData<VersionedTextResponse> getApostlesCreed() {
		return RsData.ok("사도신경 조회 성공", appendixFacade.getApostlesCreed());
	}

	@GetMapping("api/v1/appendix/ten-commandments")
	public RsData<TenCommandmentsResponse> getTenCommandments() {
		return RsData.ok("십계명 조회 성공", appendixFacade.getTenCommandments());
	}

	@GetMapping("api/v1/appendix/responsive-readings")
	public RsData<List<ResponsiveReadingResponse>> getResponsiveReadings() {
		return RsData.ok("교독문 목록 조회 성공", appendixFacade.getResponsiveReadings());
	}

	@GetMapping("api/v1/appendix/responsive-readings/{number}")
	public RsData<ResponsiveReadingResponse> getResponsiveReading(@PathVariable Integer number) {
		return RsData.ok("교독문 상세 조회 성공", appendixFacade.getResponsiveReading(number));
	}
}