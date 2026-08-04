package com.bnote.global.exception;

import com.bnote.global.response.RsData;
import com.bnote.global.response.RsStatus;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

	private final String resultCode;
	private final String msg;

	public ServiceException(String resultCode, String msg) {
		super(resultCode + ":" + msg);
		this.resultCode = resultCode;
		this.msg = msg;
	}

	public RsData<Void> getRsData() {
		return new RsData<>(resultCode, msg, null);
	}

	public static ServiceException badRequest(String msg) {
		return new ServiceException(RsStatus.BAD_REQUEST.getResultCode(), msg);
	}

	public static ServiceException unauthorized(String msg) {
		return new ServiceException(RsStatus.UNAUTHORIZED.getResultCode(), msg);
	}

	public static ServiceException forbidden(String msg) {
		return new ServiceException(RsStatus.FORBIDDEN.getResultCode(), msg);
	}

	public static ServiceException notFound(String msg) {
		return new ServiceException(RsStatus.NOT_FOUND.getResultCode(), msg);
	}
}