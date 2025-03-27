package com.roberto.form.repository;

import com.roberto.form.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  CandidateRepository extends JpaRepository<Candidato, Long> {
  
}
