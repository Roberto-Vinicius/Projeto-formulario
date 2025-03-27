package com.roberto.form.repository;

import com.roberto.form.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  CandidateRepository extends JpaRepository<Candidato, Long> {
  
}
