package utils.validators;

import database.entity.forms.RegistrationForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> paramClass) {
        return RegistrationForm.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "countryId", "id.required");

        RegistrationForm registrationForm = (RegistrationForm) obj;
       /* if(registrationForm.getCountryId() <=0){
            errors.rejectValue("countryId", "negativeValue", new Object[]{"'id'"}, "countryId can't be negative");
        }*/

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required");
        if(registrationForm.getUsername().length() < 5 || registrationForm.getUsername().length() > 100){
            errors.rejectValue("email", "lengthError", new Object[]{"'email'"}, "Email's length must be between 6 and 100");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
        if(registrationForm.getUsername().length() < 5 || registrationForm.getUsername().length() > 100){
            errors.rejectValue("password", "lengthError", new Object[]{"'password'"}, "Password's length must be between 6 and 100");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordAgain", "passwordAgain.required");
        if(!registrationForm.getPassword().equals(registrationForm.getPasswordRepeat())){
            errors.rejectValue("email", "lengthError", new Object[]{"'email'"}, "The password fields must match ");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required");
        if(registrationForm.getUsername().length() < 5 || registrationForm.getUsername().length() > 100){
            errors.rejectValue("username", "lengthError", new Object[]{"'username'"}, "Username's length must be between 6 and 100");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "label.gender", "gender.required");
        /*String gender = registrationForm.getGender();
        if(gender.equals("мужской") || gender.equals("женский")){
            errors.rejectValue("label.gender", "genderError", new Object[]{"'gender'"}, "Invalid gender value");
        }
*/
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cookieId", "cookieId.required");

    }
}
