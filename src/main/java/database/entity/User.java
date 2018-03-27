package database.entity;


import database.dao.postgresDao.CountryDao;
import database.dao.postgresDao.RightDao;
import database.entity.dop.UserForm;
import database.exceptions.DbException;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import utils.DbWrapper;
import utils.validators.GenderConstraint;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class User implements Identified,Serializable {

    @NotNull(message = "Id can't be null")
    @Min(value = 1,message = "Id must be greater than 1")
    private Integer id;

    @NotBlank(message = "Email can't be null")
    @Size(min = 5,max = 100,message = "Email's length must be between 6 and 100")
    @Email(message = "Email has to be correct")
    private String email;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 6,max = 100,message = "Password's length must be between 6 and 100")
    private String password;

    @NotBlank(message = "Username can't be empty")
    @Size(min = 6,max = 100,message = "Username's length must be between 6 and 100")
    private String username;

    @NotEmpty(message = "User should live in some country")
    private Country country;

    @NotEmpty(message = "User should have some rights")
    private Right rights;

    @NotBlank(message = "Gender can't be empty")
    @GenderConstraint
    private String gender;

    @NotNull(message = "User should have some cookie")
    private String cookieId;

    public User() {
    }

    public User(UserForm userForm) throws DbException {
        this.username = userForm.getUsername();
        this.email = userForm.getEmail();
        this.password = userForm.getPassword();
        this.gender = userForm.getGender();
        setCountry(userForm.getCountryId());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            if(this.getId().equals(((User) obj).getId())){
                return true;
            }
        }
        return false;
    }

    public User(String email, String password, String username, Integer countryId, String gender) throws DbException {
        this.email = email;
        this.username = username;
        this.password = password;
        setCountry(countryId);
        this.gender = gender;
    }



    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setCountry(Integer countryId) throws DbException {
        try {
            this.country = new CountryDao(DbWrapper.getConnection()).find(countryId);
        }catch (DbException ex){
            throw new DbException("Страна пользователя не найдена в БД(ошибка на сервере)");
        }

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public Right getRights() {
        return rights;
    }

    public void setRights(Right rights){
        this.rights = rights;
    }

    public void setRights(Integer rightsId) {

        try {
            this.rights = new RightDao(DbWrapper.getConnection()).find(rightsId);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}

