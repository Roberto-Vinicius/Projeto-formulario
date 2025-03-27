package com.roberto.form.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.roberto.form.entity.Candidato;
import com.roberto.form.repository.CandidateRepository;

import java.time.LocalDateTime;

@Configuration
public class TestConfig implements CommandLineRunner {
  
  @Autowired
  private CandidateRepository candidateRepository;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("🏁 Inserindo candidato no banco...");
    
    candidateRepository.save(new Candidato(
      null, // ID gerado automaticamente
      "João Silva",
      "joao@email.com",
      "(11) 98765-4321",
      "Desenvolvedor Java",
      "Graduação",
      "Experiência com Spring Boot",
      "form//currículo.pdf",
      "192.168.1.1",
      LocalDateTime.now()
  ));
  System.out.println("✅ Candidato inserido com sucesso!");
  }
}

