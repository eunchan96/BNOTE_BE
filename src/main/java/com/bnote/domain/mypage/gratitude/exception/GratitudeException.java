package com.bnote.domain.mypage.gratitude.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class GratitudeException extends ServiceException {

	public GratitudeException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	// 404-1
	public static GratitudeException notFound() {
		return new GratitudeException(RsStatus.NOT_FOUND.getResultCode() + "-1", "존재하지 않는 감사노트입니다.");
	}

	// 403-1
	public static GratitudeException accessDenied() {
		return new GratitudeException(RsStatus.FORBIDDEN.getResultCode() + "-1", "본인의 감사노트만 접근할 수 있습니다.");
	}
}