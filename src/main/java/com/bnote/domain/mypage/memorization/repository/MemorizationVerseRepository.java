package com.bnote.domain.mypage.memorization.repository;

import com.bnote.domain.mypage.memorization.entity.MemorizationVerse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemorizationVerseRepository extends JpaRepository<MemorizationVerse, Long> {

	List<MemorizationVerse> findByGroupIdOrderByCreateDateDesc(Long groupId);
}