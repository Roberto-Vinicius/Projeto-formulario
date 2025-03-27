package com.roberto.form.util;

import com.roberto.form.validation.ValidationFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidationFileUtil implements ConstraintValidator<ValidationFile, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
      if (value == null || value.isEmpty()) {
          return false; // Arquivo obrigatório
      }
      return value.matches("^.*\\.(doc|docx|pdf)$");
  }
}