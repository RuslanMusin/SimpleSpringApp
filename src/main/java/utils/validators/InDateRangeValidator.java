package utils.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class InDateRangeValidator implements ConstraintValidator<InDateRange, Date> {

    private final SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");

    private InDateRange constraintAnnotation;

    private Date secondMax;

    @Override
    public void initialize(InDateRange constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR) - 10);
        secondMax = calendar.getTime();
    }

    @Override
    public boolean isValid(java.util.Date value, ConstraintValidatorContext context) {
        try {
            Date min = dateParser.parse(constraintAnnotation.min());
            Date max = dateParser.parse(constraintAnnotation.max());

            if(max.after(secondMax)) {
                max = secondMax;
            }
            return value == null ||
                    (value.after(min) && value.before(max));
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
}
