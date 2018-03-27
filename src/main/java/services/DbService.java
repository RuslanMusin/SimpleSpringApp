package services;


import database.dao.IDao.IMessageDao;
import database.dao.postgresDao.CountryDao;
import database.dao.postgresDao.UserDao;
import database.entity.Country;
import database.entity.IMessage;
import database.entity.User;
import database.entity.dop.UserForm;
import database.exceptions.DbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import utils.DbWrapper;

import java.util.List;

@org.springframework.stereotype.Service
@Profile("database")
public class DbService implements Service{

    private IMessageDao messageDao;

    @Autowired
    public DbService(IMessageDao messageDao, DbWrapper dbWrapper) throws DbException {
        this.messageDao = messageDao;
        this.messageDao.setConnection(dbWrapper.getConnection());

    }

    public String getMessage() {
        String message = "no message found";
        try {
            List<IMessage> messages = messageDao.findAll();
            if(messages.size() > 0) {
                message = messages.get(0).getMessage();
            }
        } catch (DbException e) {
            System.out.println(e.getMessage());
        }

        return message;
    }

    public void insertUser(UserForm userForm) throws DbException {
        UserDao userDao = new UserDao(DbWrapper.getConnection());

        User user = new User(userForm);

        System.out.println("user:\n");
        System.out.println(user.getCountry().getName());
        System.out.println(user.getUsername());

        user.setCookieId("2343242423433");

        System.out.println("insertUser");

        userDao.save(user);
    }

    public User getUser(UserForm userForm) throws DbException {
        UserDao userDao = new UserDao(DbWrapper.getConnection());

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

    public List<Country> findAllCountries() throws DbException {
        return new CountryDao(DbWrapper.getConnection()).findAll();
    }

    public void deleteUser(int id) throws DbException {
        new UserDao(DbWrapper.getConnection()).delete(id);
    }

    public void updateUser(User user) throws DbException {
        new UserDao(DbWrapper.getConnection()).update(user);
    }
}
