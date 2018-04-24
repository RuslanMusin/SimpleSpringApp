package services;

import database.entity.User;
import database.entity.UserAuthority;
import database.entity.forms.LoginForm;
import database.entity.forms.UserChangeForm;
import database.entity.Gender;
import database.entity.forms.RegistrationForm;
import database.hibernate_repository.UserAuthorityRepository;
import database.hibernate_repository.UserRepository;
import exceptions.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import exceptions.DbException;
import utils.Const;

import java.io.UnsupportedEncodingException;


import static utils.Const.USUAL_ROLE;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepo;

    private PasswordEncoder passwordEncoder;

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        User user = userRepository.findByUsername(username);

        logger.debug("loadUser");
        if (user == null) {
            System.out.println("load user = null");
            throw new UsernameNotFoundException(
                    "No user found with username: "+ username);


        }

        return user;
    }

    @Transactional
    public void registerUser(RegistrationForm registrationForm) {

        User user = new User();
        UserAuthority authority = userAuthorityRepo.findByAuthority(USUAL_ROLE);

        user.setUsernameReal(registrationForm.getUsernameReal());
        user.setUsername(registrationForm.getUsername());
        user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        user.setGender(Gender.valueOf(registrationForm.getGender()));
        user.setCountry(registrationForm.getCountry());
        user.addAuthority(authority);

        userRepository.save(user);

        System.out.println("saved");
    }

    public User getUser(LoginForm loginForm) {

        System.out.println("registrationForm : " + loginForm.getUsername());

        User user = userRepository.findByUsername(loginForm.getUsername());

        if(user == null){
            throw new NotFoundException(Const.KEY_USER_TYPE);
        }

        System.out.println("user:\n");
        System.out.println(user.getCountry().getName());
        System.out.println(user.getUsernameReal());

        return user;
    }

    public void deleteUser(int id){
        userRepository.delete(id);
    }

    public void updateUser(User user,UserChangeForm userChangeForm) {
        user.setUsernameReal(userChangeForm.getUsernameReal());
        user.setCountry(userChangeForm.getCountry());
        user.setGender(Gender.valueOf(userChangeForm.getGender()));

        userRepository.save(user);
    }


}
