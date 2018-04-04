package database.dao.postgresDao;

import database.dao.abstractDao.GenericDao;
import database.entity.User;
import org.springframework.stereotype.Repository;
import utils.exceptions.DbException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserEntDao implements GenericDao<User>{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(User object) throws DbException {
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    @Override
    public User find(Integer id) throws DbException {
        em.getTransaction().begin();
        User user = em.find(User.class, 1);
        em.getTransaction().commit();

        return user;
    }

    @Override
    public void update(User object) throws DbException {
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
       /* employee.setFirstname("Pranil");
        System.out.println("Employee after updation :- " + employee);
        em.getTransaction().commit();*/
    }

    @Override
    public void delete(Integer id) throws DbException {
        em.getTransaction().begin();

        em.createQuery("select u from database.entity.User u where u.user_id=:id")
                .setParameter("id", id)
                .executeUpdate();

        em.getTransaction().commit();
    }

    @Override
    public List<User> findAll() throws DbException {
        em.getTransaction().begin();
        Query query = em.createQuery("select u from database.entity.User u",User.class);
        List<User> users = (List<User>)query.getResultList();
        em.getTransaction().commit();

        return users;
    }
}
