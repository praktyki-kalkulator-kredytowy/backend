package com.praktyki.backend.web.validation;

import com.praktyki.backend.business.services.InstallmentScheduleService;
import com.praktyki.backend.business.value.ScheduleConfiguration;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidInterestRateValidator.class)
public @interface ValidInterestRate {
    //TODO: add upper bound (max)
    String message() default "Interest rate must be higher than "
            + InstallmentScheduleService.MIN_INTEREST_RATE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
