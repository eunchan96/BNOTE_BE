package com.bnote.domain.mypage.copyformat.repository;

import com.bnote.domain.mypage.copyformat.entity.CopyFormatPreset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CopyFormatPresetRepository extends JpaRepository<CopyFormatPreset, Long> {

	List<CopyFormatPreset> findByMemberIdOrderByCreateDateDesc(Long memberId);
}