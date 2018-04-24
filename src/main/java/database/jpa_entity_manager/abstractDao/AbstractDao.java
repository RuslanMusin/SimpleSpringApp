package database.jpa_entity_manager.abstractDao;
import database.entity.Identified;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public abstract class AbstractDao<T extends Identified> implements GenericDao<T> {

    protected EntityManagerFactory entityManagerFactory;

    public AbstractDao() {
    }

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(T entity) {
        EntityManager entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    @Override
    public List<T> findAll() {
        EntityManager entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        String hql = getAllQuery();
        List entities = entityManager
                .createQuery(hql)
                .getResultList();
        entityManager.getTransaction().commit();

        entityManager.close();
        return entities;
    }

    @Override
    public T find(Integer id) {
        EntityManager entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        T entity = entityManager.find(getEntityClass(), id);
        entityManager.getTransaction().commit();

        entityManager.close();
        return entity;
    }

    @Override
    public void update(T entity) {
        EntityManager entityManager = getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    @Override
    public void delete(Integer id) {
        EntityManager entityManager = getEntityManager();

        T entity = entityManager.find(getEntityClass(), id);

        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    protected abstract String getAllQuery();

    protected abstract Class<T> getEntityClass();
}
