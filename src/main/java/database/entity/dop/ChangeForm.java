package database.entity.dop;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ChangeForm {

    @Size(min = 5,max = 100,message = "{error.length}")
    private String username;

    private String gender;

    @Min(value = 1,message = "{error.min_id}")
    private Integer countryId;

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
}
