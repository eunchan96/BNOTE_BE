package com.bnote.domain.appendix.repository;

import com.bnote.domain.appendix.entity.AppendixText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppendixTextRepository extends JpaRepository<AppendixText, String> {
}