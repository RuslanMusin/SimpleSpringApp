package database.jpa_entity_manager.IDao;

import database.entity.User;
import database.jdbc_template.abstractDao.GenericDao;
import org.springframework.stereotype.Repository;
import utils.exceptions.DbException;

@Repository
public interface IUserDao extends GenericDao<User> {

    public User findByEmail(String email);
}
