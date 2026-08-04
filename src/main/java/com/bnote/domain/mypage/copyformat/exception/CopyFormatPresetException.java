package com.bnote.domain.mypage.copyformat.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class CopyFormatPresetException extends ServiceException {

	public CopyFormatPresetException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	// 404-1
	public static CopyFormatPresetException notFound() {
		return new CopyFormatPresetException(RsStatus.NOT_FOUND.getResultCode() + "-1", "존재하지 않는 복사 형식 프리셋입니다.");
	}

	// 403-1
	public static CopyFormatPresetException accessDenied() {
		return new CopyFormatPresetException(RsStatus.FORBIDDEN.getResultCode() + "-1", "본인의 프리셋만 삭제할 수 있습니다.");
	}
}