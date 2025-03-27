package com.roberto.form.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import com.roberto.form.util.ValidationFileUtil;

@Documented
@Constraint(validatedBy = ValidationFileUtil.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationFile {
  String message() default "O arquivo deve estar nos formatos .doc, .docx ou .pdf";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
