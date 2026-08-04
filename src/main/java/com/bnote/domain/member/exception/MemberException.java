package com.bnote.domain.member.exception;

import com.bnote.global.exception.ServiceException;
import com.bnote.global.response.RsStatus;

public class MemberException extends ServiceException {

	public MemberException(String resultCode, String msg) {
		super(resultCode, msg);
	}

	// ======================================= not found ======================================= //
	private static MemberException notFound(int detailCode, String msg) {
		return new MemberException(RsStatus.NOT_FOUND.getResultCode() + "-" + detailCode, msg);
	}

	// 404-1
	public static MemberException notFound() {
		return notFound(1, "존재하지 않는 회원입니다.");
	}

	// ======================================= unauthorized ======================================= //
	private static MemberException unauthorized(int detailCode, String msg) {
		return new MemberException(RsStatus.UNAUTHORIZED.getResultCode() + "-" + detailCode, msg);
	}

	// 401-1
	public static MemberException loginRequired() {
		return unauthorized(1, "로그인이 필요합니다.");
	}

	// 401-2
	public static MemberException invalidRefreshToken() {
		return unauthorized(2, "유효하지 않은 refresh token 입니다.");
	}

	// 401-3
	public static MemberException refreshTokenMismatch() {
		return unauthorized(3, "일치하지 않는 refresh token 입니다.");
	}

	// 401-4
	public static MemberException expiredToken() {
		return unauthorized(4, "만료된 토큰입니다.");
	}

	// 401-5
	public static MemberException invalidToken() {
		return unauthorized(5, "유효하지 않은 토큰입니다.");
	}

	// ======================================= bad request ======================================= //
	private static MemberException badRequest(int detailCode, String msg) {
		return new MemberException(RsStatus.BAD_REQUEST.getResultCode() + "-" + detailCode, msg);
	}

	// 400-1
	public static MemberException unsupportedSocialType(String socialType) {
		return badRequest(1, "지원하지 않는 소셜 로그인입니다: " + socialType);
	}

	// 400-2
	public static MemberException oauthFailed(String msg) {
		return badRequest(2, msg);
	}
}