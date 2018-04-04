package services;


import database.entity.Country;
import database.entity.Right;
import database.entity.User;
import database.entity.forms.ChangeForm;
import database.entity.forms.UserForm;
import database.repository.CountryRepository;
import database.repository.RightRepository;
import database.repository.UserRepository;
import utils.exceptions.DbException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class DbService implements Service{

    private UserRepository userRepo;

    private CountryRepository countryRepo;

    private RightRepository rightRepo;

    @Autowired
    public DbService(UserRepository userRepo, CountryRepository countryRepo, RightRepository rightRepo) throws DbException {
        this.userRepo = userRepo;
        this.countryRepo = countryRepo;
        this.rightRepo = rightRepo;
    }

    public void insertUser(UserForm userForm) throws DbException {
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setGender(userForm.getGender());
        user.setCountry(findCountry(userForm.getCountryId()));
        user.setRights(findRights(2));

        System.out.println("user:\n");
        System.out.println(user.getCountry().getName());
        System.out.println(user.getUsername());

        user.setCookieId("2343242423433");

        System.out.println("insertUser");

        userRepo.save(user);
    }

    public User getUser(UserForm userForm) throws DbException {

        System.out.println("userForm : " + userForm.getEmail());

        User user = userRepo.findByEmail(userForm.getEmail());

        if(user == null){
            throw new DbException("user not found");
        }

        System.out.println("user:\n");
        System.out.println(user.getCountry().getName());
        System.out.println(user.getUsername());

        return user;
    }

    public void deleteUser(int id) throws DbException {
        userRepo.delete(id);
    }

    public void updateUser(User user,ChangeForm changeForm) throws DbException {
        user.setUsername(changeForm.getUsername());
        user.setCountry(findCountry(changeForm.getCountryId()));
        user.setGender(changeForm.getGender());

        userRepo.save(user);
    }

    public List<Country> findAllCountries() throws DbException {
        return (List<Country>) countryRepo.findAll();
    }

    public Country findCountry(Integer id) throws DbException {
        return countryRepo.findOne(id);
    }

    public Right findRights(Integer id) throws DbException {
        return rightRepo.findOne(id);
    }
}
