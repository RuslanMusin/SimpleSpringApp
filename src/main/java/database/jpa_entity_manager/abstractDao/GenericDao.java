package database.jpa_entity_manager.abstractDao;

import database.entity.Identified;
import org.springframework.stereotype.Repository;
import utils.exceptions.DbException;

import java.util.List;

@Repository
public interface GenericDao<T extends Identified> {

    public void save(T object);

    public T find(Integer id);

    public void update(T object);

    public void delete(Integer id);

    public List<T> findAll();
}
