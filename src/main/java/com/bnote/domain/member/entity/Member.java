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

	@Column(nullable = false)
	private String nickname;

	@Column(name = "profile_image_url")
	private String profileImageUrl;

	@Column(name = "refresh_token")
	private String refreshToken;

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

	public void updateProfile(String nickname, String profileImageUrl) {
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}
}