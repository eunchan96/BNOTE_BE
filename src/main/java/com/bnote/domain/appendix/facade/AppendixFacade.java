package com.bnote.domain.appendix.facade;

import com.bnote.domain.appendix.dto.response.ResponsiveReadingResponse;
import com.bnote.domain.appendix.dto.response.TenCommandmentsResponse;
import com.bnote.domain.appendix.dto.response.VersionedTextResponse;
import com.bnote.domain.appendix.service.AppendixService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppendixFacade {

	private final AppendixService appendixService;

	public VersionedTextResponse getLordsPrayer() {
		return appendixService.getLordsPrayer();
	}

	public VersionedTextResponse getApostlesCreed() {
		return appendixService.getApostlesCreed();
	}

	public TenCommandmentsResponse getTenCommandments() {
		return appendixService.getTenCommandments();
	}

	public List<ResponsiveReadingResponse> getResponsiveReadings() {
		return appendixService.getResponsiveReadings();
	}

	public ResponsiveReadingResponse getResponsiveReading(int number) {
		return appendixService.getResponsiveReading(number);
	}
}