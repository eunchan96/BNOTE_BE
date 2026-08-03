package com.bnote.domain.member.repository;

import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.entity.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}