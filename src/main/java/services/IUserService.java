package services;

import database.entity.Gender;
import database.entity.User;
import database.entity.forms.LoginForm;
import database.entity.forms.RegistrationForm;
import database.entity.forms.UserChangeForm;
import exceptions.DbException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface IUserService extends UserDetailsService {

    void registerUser(RegistrationForm registrationForm);

    User getUser(LoginForm loginForm);

    void deleteUser(int id);

    void updateUser(User user,UserChangeForm userChangeForm);
}
