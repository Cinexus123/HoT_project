package com.api.validation.annotations;

import com.api.validation.validators.FloorCustomValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FloorCustomValidator.class)
@Documented
public @interface ValidFloor {

    String message() default "Uploaded file is not valid due to bad extension, name format or name length";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };

    String regex();
    short nameLength() default 0;
}
