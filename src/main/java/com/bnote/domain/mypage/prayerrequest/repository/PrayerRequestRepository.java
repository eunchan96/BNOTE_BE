package com.bnote.domain.mypage.prayerrequest.repository;

import com.bnote.domain.mypage.prayerrequest.entity.PrayerRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrayerRequestRepository extends JpaRepository<PrayerRequest, Long> {

	List<PrayerRequest> findByMemberIdOrderByCreateDateDesc(Long memberId);
}