package com.bnote.domain.member.entity;

import com.bnote.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
		name = "members",
		uniqueConstraints = @UniqueConstraint(columnNames = {"social_type", "social_id"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(name = "social_type", nullable = false, length = 20)
	private SocialType socialType;

	@Column(name = "social_id", nullable = false)
	private String socialId;

	/** 소셜 로그인 제공자가 준 닉네임. 로그인할 때마다 최신값으로 갱신됨. */
	@Column(nullable = false)
	private String nickname;

	/** 소셜 로그인 제공자가 준 프로필 사진. 로그인할 때마다 최신값으로 갱신됨. */
	@Column(name = "profile_image_url")
	private String profileImageUrl;

	@Column(name = "refresh_token")
	private String refreshToken;

	// ===== 마이페이지 프로필 (사용자가 직접 입력, 소셜 로그인 값과 무관) =====

	private String name;

	@Column(name = "church")
	private String church;

	@Column(name = "department")
	private String department;

	private String position;

	/** 사용자가 직접 업로드한 프로필 사진. profileImageUrl(소셜 제공)과 별개. */
	@Column(name = "photo_url")
	private String photoUrl;

	@Builder
	private Member(SocialType socialType, String socialId, String nickname, String profileImageUrl) {
		this.socialType = socialType;
		this.socialId = socialId;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/** 소셜 로그인 시 제공자 쪽 정보(닉네임/프로필 사진)를 최신값으로 갱신 */
	public void updateSocialProfile(String nickname, String profileImageUrl) {
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}

	/** 마이페이지에서 사용자가 직접 입력하는 프로필 정보 갱신 */
	public void updateMyPageProfile(String name, String church, String department, String position) {
		this.name = name;
		this.church = church;
		this.department = department;
		this.position = position;
	}

	public void updatePhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}