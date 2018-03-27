package utils.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderConstraintValidator implements
        ConstraintValidator<GenderConstraint, String> {

    @Override
    public void initialize(GenderConstraint genderConstraint) {
    }

    @Override
    public boolean isValid(String contactField,
                           ConstraintValidatorContext cxt) {
        return contactField.equals("мужской") || contactField.equals("женский");
    }

}
