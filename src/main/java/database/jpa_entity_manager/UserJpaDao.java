package database.jpa_entity_manager;

import database.entity.Country;
import database.entity.User;
import database.jpa_entity_manager.IDao.IUserDao;
import database.jpa_entity_manager.abstractDao.AbstractDao;
import org.springframework.stereotype.Repository;
import utils.exceptions.DbException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserJpaDao extends AbstractDao<User> implements IUserDao {

    @Override
    protected String getAllQuery() {
        return "from User";
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    public User findByEmail(String email) {

        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        User user = (User) em.createQuery("from User u where username=:userEmail")
                .setParameter("userEmail", email)
                .getSingleResult();

        em.getTransaction().commit();

        return user;
    }

}
