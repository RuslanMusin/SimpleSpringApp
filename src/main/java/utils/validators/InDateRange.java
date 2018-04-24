package utils.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Date;
import java.util.GregorianCalendar;

@Documented
@Constraint(validatedBy = InDateRangeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InDateRange {

    Date date = new Date();

    String min() default "-1500-01-01";

    String max() default "2999-12-31";

    String message() default "{validation.date.InDateRange.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
