package database.jdbc_template.IDao;

import database.jdbc_template.abstractDao.GenericDao;
import database.entity.User;
import utils.exceptions.DbException;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends GenericDao<User> {

   /* public String getSelectByCookieId();

    public String getSelectByEmail() ;

    public String getSelectCookieId(String cookieId);

    public String getSelectPassword();

    public String getSelectDoubleEmail(String email);

    public boolean findCookieIdMatch(String cookieId) throws DbException;

    public boolean findDoubleEmail(String email) throws DbException ;

    public User findByCookieId(String cookieId) throws DbException ;

    public String findPassword(String email) throws DbException ;*/

    public User findByEmail(String email) throws DbException;
}
