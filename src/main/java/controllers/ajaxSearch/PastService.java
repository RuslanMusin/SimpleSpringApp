package services;


import database.entity.Country;
import database.entity.User;
import database.entity.forms.ChangeForm;
import database.entity.forms.UserForm;
import database.jpa_entity_manager.CountryJpaDao;
import database.jpa_entity_manager.UserJpaDao;
import org.springframework.beans.factory.annotation.Autowired;
import utils.exceptions.DbException;

import java.util.List;

@org.springframework.stereotype.Service
public class PastService {

    private UserJpaDao userJpaDao;

    private CountryJpaDao countryJpaDao;

    @Autowired
    public PastService(UserJpaDao userJpaDao, CountryJpaDao countryJpaDao) {
        this.userJpaDao = userJpaDao;
        this.countryJpaDao = countryJpaDao;
    }


    public void insertUser(UserForm userForm) throws DbException {
        User user = new User();
        user.setUsernameReal(userForm.getUsernameReal());
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        user.setGender(userForm.getGender());
//        user.setCountry(findCountry(userForm.getCountryId()));
//        user.setRights(findRights(2));

        System.out.println("user:\n");
        System.out.println(user.getCountry().getName());
        System.out.println(user.getUsernameReal());

//        user.setCookieId("2343242423433");

        System.out.println("insertUser");

        userJpaDao.save(user);
    }

    public User getUser(UserForm userForm) throws DbException {

        System.out.println("userForm : " + userForm.getUsername());

        User user = userJpaDao.findByEmail(userForm.getUsername());

        if(user == null){
            throw new DbException("user not found");
        }

        System.out.println("user:\n");
        System.out.println(user.getCountry().getName());
        System.out.println(user.getUsernameReal());

        return user;
    }

    public void deleteUser(int id) throws DbException {
        userJpaDao.delete(id);
    }

    public void updateUser(User user,ChangeForm changeForm) throws DbException {
        user.setUsernameReal(changeForm.getUsername());
//        user.setCountry(findCountry(changeForm.get()));
        user.setGender(changeForm.getGender());

        userJpaDao.update(user);
    }

    public List<Country> findAllCountries() throws DbException {
        return (List<Country>) countryJpaDao.findAll();
    }

    public Country findCountry(Integer id) throws DbException {
        return countryJpaDao.find(id);
    }


}
