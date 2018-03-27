package database.entity.dop;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import utils.validators.FieldMatch;
import utils.validators.GenderConstraint;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


/*@FieldMatch.List({
        @FieldMatch(first = "password", second = "passwordAgain",
                message = "The password fields must match")
})*/
public class UserForm {

    @NotBlank(message = "{error.not_empty}")
    @Size(min = 5,max = 100,message = "{error.length}")
    @Email(message = "{error.invalid_email}")
    private String email;

    @NotBlank(message = "{error.not_empty}")
    @Size(min = 5,max = 100,message = "{error.length}")
    private String password;

    @Size(min = 5,max = 100,message = "{error.length}")
    private String passwordAgain;

    @Size(min = 5,max = 100,message = "{error.length}")
    private String username;

    private String gender;

    @Min(value = 1,message = "{error.min_id}")
    private Integer countryId;

    private String cookieId;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }
}
