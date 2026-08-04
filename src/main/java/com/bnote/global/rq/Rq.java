package com.bnote.global.rq;

import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.exception.MemberException;
import com.bnote.domain.member.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Rq {

	private final MemberRepository memberRepository;

	public Rq(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	/**
	 * 현재 요청의 JWT에서 추출된 로그인 회원 id. 비로그인 요청이면 null.
	 */
	public Long getActorId() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.map(Authentication::getPrincipal)
				.filter(principal -> principal instanceof Long)
				.map(principal -> (Long) principal)
				.orElse(null);
	}

	/**
	 * 반드시 로그인이 필요한 지점에서 사용. 비로그인이면 401 예외를 던진다.
	 */
	public Long getActorIdOrThrow() {
		Long actorId = getActorId();
		if (actorId == null) {
			throw MemberException.loginRequired();
		}
		return actorId;
	}

	public Member getActorFromDb() {
		return memberRepository.findById(getActorIdOrThrow())
				.orElseThrow(MemberException::notFound);
	}
}