package com.bnote.domain.appendix.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class AppendixException extends ServiceException {

	public AppendixException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	private static AppendixException notFound(int detailCode, String msg) {
		return new AppendixException(RsStatus.NOT_FOUND.getResultCode() + "-" + detailCode, msg);
	}

	// 404-1
	public static AppendixException lordsPrayerNotFound() {
		return notFound(1, "주기도문 데이터가 없습니다.");
	}

	// 404-2
	public static AppendixException apostlesCreedNotFound() {
		return notFound(2, "사도신경 데이터가 없습니다.");
	}

	// 404-3
	public static AppendixException tenCommandmentsNotFound() {
		return notFound(3, "십계명 데이터가 없습니다.");
	}

	// 404-4
	public static AppendixException responsiveReadingNotFound(int number) {
		return notFound(4, "존재하지 않는 교독문입니다: " + number);
	}
}