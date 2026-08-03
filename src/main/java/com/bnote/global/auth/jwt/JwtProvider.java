package com.bnote.global.auth.jwt;

import com.bnote.global.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

	private static final String MEMBER_ID_CLAIM = "memberId";

	private final SecretKey key;
	private final long accessTokenExpireMillis;
	private final long refreshTokenExpireMillis;

	public JwtProvider(
			@Value("${jwt.secret}") String secret,
			@Value("${jwt.access-token-expire-seconds}") long accessTokenExpireSeconds,
			@Value("${jwt.refresh-token-expire-seconds}") long refreshTokenExpireSeconds
	) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.accessTokenExpireMillis = accessTokenExpireSeconds * 1000;
		this.refreshTokenExpireMillis = refreshTokenExpireSeconds * 1000;
	}

	public String generateAccessToken(Long memberId) {
		return generateToken(memberId, accessTokenExpireMillis);
	}

	public String generateRefreshToken(Long memberId) {
		return generateToken(memberId, refreshTokenExpireMillis);
	}

	private String generateToken(Long memberId, long expireMillis) {
		Date now = new Date();
		return Jwts.builder()
				.claim(MEMBER_ID_CLAIM, memberId)
				.issuedAt(now)
				.expiration(new Date(now.getTime() + expireMillis))
				.signWith(key)
				.compact();
	}

	public Long getMemberId(String token) {
		return parseClaims(token).get(MEMBER_ID_CLAIM, Long.class);
	}

	public boolean isValid(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

	private Claims parseClaims(String token) {
		try {
			return Jwts.parser()
					.verifyWith(key)
					.build()
					.parseSignedClaims(token)
					.getPayload();
		} catch (ExpiredJwtException e) {
			throw new ServiceException("401-3", "만료된 토큰입니다.");
		} catch (JwtException | IllegalArgumentException e) {
			throw new ServiceException("401-4", "유효하지 않은 토큰입니다.");
		}
	}
}