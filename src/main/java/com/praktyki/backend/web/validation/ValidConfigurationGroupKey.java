package com.praktyki.backend.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidConfigurationGroupKeyValidator.class)
public @interface ValidConfigurationGroupKey {
    String message() default "Invalid Configuration group key name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
