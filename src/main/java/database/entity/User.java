package database.entity;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import utils.validators.GenderConstraint;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Identified,Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true, nullable = false)
//    @NotNull(message = "Id can't be null")
    private Integer id;

    @Column(unique = true)
    @NotBlank(message = "Email can't be null")
    @Size(min = 5,max = 100,message = "Email's length must be between 6 and 100")
    @Email(message = "Email has to be correct")
    private String email;

    @Column
    @NotBlank(message = "Password can't be empty")
    @Size(min = 6,max = 100,message = "Password's length must be between 6 and 100")
    private String password;

    @Column
    @NotBlank(message = "Username can't be empty")
    @Size(min = 6,max = 100,message = "Username's length must be between 6 and 100")
    private String username;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name="user_right",referencedColumnName = "right_id")
    private Right rights;

    @Column
    @NotBlank(message = "Gender can't be empty")
    @GenderConstraint
    private String gender;

    @Column(name = "cookie_id")
    @NotNull(message = "User should have some cookie")
    private String cookieId;

    public User() {
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

}

