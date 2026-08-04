package com.bnote.domain.sermon.repository;

import com.bnote.domain.sermon.entity.Sermon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SermonRepository extends JpaRepository<Sermon, Long> {

	List<Sermon> findByMemberIdOrderBySermonDateDesc(Long memberId);

	List<Sermon> findByMemberIdAndTitleContainingOrderBySermonDateDesc(Long memberId, String keyword);
}