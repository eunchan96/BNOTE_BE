package com.bnote.domain.mypage.profile.service;

import com.bnote.domain.member.entity.Member;
import com.bnote.domain.member.entity.SocialType;
import com.bnote.domain.member.repository.MemberRepository;
import com.bnote.domain.mypage.profile.dto.request.ProfileRequest;
import com.bnote.domain.mypage.profile.dto.response.ProfileResponse;
import com.bnote.global.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MemberRepository memberRepository;

    private Long memberId;

    @BeforeEach
    void setUp() {
        Member member = memberRepository.save(
                Member.builder().socialType(SocialType.KAKAO).socialId("profile-service-test").nickname("카카오닉네임").build()
        );
        memberId = member.getId();
    }

    @Test
    @DisplayName("마이페이지 프로필을 아직 입력 안 했으면 빈 값으로 응답한다")
    void t1() {
        ProfileResponse response = profileService.getProfile(memberId);

        assertThat(response.name()).isNull();
    }

    @Test
    @DisplayName("프로필을 수정하면 값이 반영된다")
    void t2() {
        ProfileResponse response = profileService.update(
                memberId, new ProfileRequest("은찬", "은혜교회", "청년부", "성도")
        );

        assertThat(response.name()).isEqualTo("은찬");
        assertThat(response.church()).isEqualTo("은혜교회");
    }

    @Test
    @DisplayName("여러 번 수정해도 같은 회원 레코드가 갱신된다")
    void t3() {
        profileService.update(memberId, new ProfileRequest("은찬", "은혜교회", "청년부", "성도"));
        ProfileResponse updated = profileService.update(memberId, new ProfileRequest("은찬2", "은혜교회", "청년부", "리더"));

        assertThat(updated.name()).isEqualTo("은찬2");
        assertThat(updated.position()).isEqualTo("리더");
    }

    @Test
    @DisplayName("존재하지 않는 회원이면 예외가 발생한다")
    void t4() {
        assertThatThrownBy(() -> profileService.getProfile(999L))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("존재하지 않는 회원");
    }
}