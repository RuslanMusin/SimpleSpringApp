package database.dao.abstractDao;

import database.entity.Identified;
import utils.exceptions.DbException;

import java.util.List;

public interface GenericDao<T extends Identified> {

    public void save(T object)  throws DbException;

    public T find(Integer id) throws DbException;

    public void update(T object) throws DbException;

    public void delete(Integer id) throws DbException;

    public List<T> findAll() throws DbException;
}
