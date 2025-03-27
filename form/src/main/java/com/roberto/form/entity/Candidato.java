package com.roberto.form.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Table(name = "Candidato")
@Entity(name = "candidato")
@Data
@AllArgsConstructor // Gera um construtor com todos os campos
@NoArgsConstructor  // Gera um construtor vazio (obrigatório para JPA)
public class Candidato {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "O nome é obrigatório.")
  @Column(nullable = false)
  private String nome;

  @Email(message = "E-mail inválido.")
  @NotBlank(message = "O e-mail é obrigatório.")
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank(message = "O telefone é obrigatório.")
  @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "Formato inválido. Use (XX) XXXXX-XXXX")
  @Column(nullable = false)
  private String telefone;

  @NotBlank(message = "O cargo desejado é obrigatório.")
  @Column(nullable = false)
  private String cargoDesejado;

  @NotBlank(message = "A escolaridade é obrigatória.")
  @Column(nullable = false)
  private String escolaridade;

  @Column(columnDefinition = "TEXT") // Permite textos longos
  private String observacoes;

  @NotBlank(message = "O arquivo do currículo é obrigatório.")
  @Column(nullable = false)
  private String arquivoPath; // Caminho do arquivo salvo

  @Column(nullable = false)
  private String ip; // IP do usuário no momento do envio

  @Column(nullable = false, updatable = false)
  private LocalDateTime dataEnvio = LocalDateTime.now(); // Data/hora do envio

}