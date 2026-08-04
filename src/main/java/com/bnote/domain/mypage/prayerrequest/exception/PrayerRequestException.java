package com.bnote.domain.mypage.prayerrequest.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class PrayerRequestException extends ServiceException {

	public PrayerRequestException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	// 404-1
	public static PrayerRequestException notFound() {
		return new PrayerRequestException(RsStatus.NOT_FOUND.getResultCode() + "-1", "존재하지 않는 기도제목입니다.");
	}

	// 403-1
	public static PrayerRequestException accessDenied() {
		return new PrayerRequestException(RsStatus.FORBIDDEN.getResultCode() + "-1", "본인의 기도제목만 접근할 수 있습니다.");
	}
}