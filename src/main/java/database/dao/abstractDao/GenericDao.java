package database.dao.abstractDao;

import database.entity.Identified;
import database.exceptions.DbException;

import java.sql.Connection;
import java.util.List;

public interface GenericDao<T extends Identified> {

    public void setConnection(Connection connection);

    public Connection getConnection();

    public void save(T object)  throws DbException;

    public T find(Integer id) throws DbException;

    public void update(T object) throws DbException;

    public void delete(Integer id) throws DbException;

    public List<T> findAll() throws DbException;
}
