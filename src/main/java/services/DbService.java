package services;


import database.dao.IDao.ICountryDao;
import database.dao.IDao.IRightDao;
import database.dao.IDao.IUserDao;
import database.entity.Country;
import database.entity.Right;
import database.entity.User;
import database.entity.dop.ChangeForm;
import database.entity.dop.UserForm;
import database.exceptions.DbException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class DbService implements Service{


    private IUserDao userDao;

    private ICountryDao countryDao;

    private IRightDao rightDao;

    @Autowired
    public DbService(IUserDao userDao, ICountryDao countryDao, IRightDao rightDao) throws DbException {
        this.userDao = userDao;
        this.countryDao = countryDao;
        this.rightDao = rightDao;
    }

    public void insertUser(UserForm userForm) throws DbException {
//        UserDao userDao = new UserDao(DbWrapper.getConnection());

        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setGender(userForm.getGender());
        user.setCountry(findCountry(userForm.getCountryId()));

        System.out.println("user:\n");
        System.out.println(user.getCountry().getName());
        System.out.println(user.getUsername());

        user.setCookieId("2343242423433");

        System.out.println("insertUser");

        userDao.save(user);
    }

    public User getUser(UserForm userForm) throws DbException {
//        UserDao userDao = new UserDao(DbWrapper.getConnection());

        System.out.println("userForm : " + userForm.getEmail());

        User user = userDao.findByEmail(userForm.getEmail());

        if(user == null){
            throw new DbException("user not found");
        }

        System.out.println("user:\n");
        System.out.println(user.getCountry().getName());
        System.out.println(user.getUsername());

        return user;
    }

    public void deleteUser(int id) throws DbException {
//        new UserDao(DbWrapper.getConnection()).delete(id);
        userDao.delete(id);
    }

    public void updateUser(User user,ChangeForm changeForm) throws DbException {
//        new UserDao(DbWrapper.getConnection()).update(user);
        user.setUsername(changeForm.getUsername());
        user.setCountry(findCountry(changeForm.getCountryId()));
        user.setGender(changeForm.getGender());

        userDao.update(user);
    }

    public List<Country> findAllCountries() throws DbException {
        return countryDao.findAll();
    }

    public Country findCountry(Integer id) throws DbException {
        return countryDao.find(id);
    }

    public Right findRights(Integer id) throws DbException {
        return rightDao.find(id);
    }
}
