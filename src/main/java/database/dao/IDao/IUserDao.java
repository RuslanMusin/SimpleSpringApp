package database.dao.IDao;

import database.dao.abstractDao.GenericDao;
import database.entity.User;
import database.exceptions.DbException;


public interface IUserDao extends GenericDao<User> {

    public String getSelectByCookieId();

    public String getSelectByEmail() ;

    public String getSelectCookieId(String cookieId);

    public String getSelectPassword();

    public String getSelectDoubleEmail(String email);

    public boolean findCookieIdMatch(String cookieId) throws DbException;

    public boolean findDoubleEmail(String email) throws DbException ;

    public User findByCookieId(String cookieId) throws DbException ;

    public User findByEmail(String email) throws DbException;

    public String findPassword(String email) throws DbException ;
}
