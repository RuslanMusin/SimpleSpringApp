package utils.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GenderConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GenderConstraint {

    String message() default "Invalid gender";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
