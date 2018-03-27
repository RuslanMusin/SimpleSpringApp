package utils.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldMatchValidator.class)
@Documented
public @interface FieldMatch {

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        FieldMatch[] value();
    }

    public String message() default "{constraints.fieldmatch}";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};

    public String first();

    public String second();
}
