package database.dao.abstractDao;

import database.entity.Identified;
import database.exceptions.DbException;

import java.sql.*;
import java.util.List;

public abstract class AbstractDao<T extends Identified> implements GenericDao<T> {

    private Connection connection;

    public abstract String getSelectQuery();

    public abstract String getCreateQuery();

    public abstract String getReadQuery(String id);

    public abstract String getUpdateQuery();

    public abstract String getDeleteQuery();

    protected abstract List<T> parseResultSet(ResultSet rs) throws DbException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws DbException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws DbException;

    public AbstractDao() {
    }

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<T> findAll() throws DbException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }

    public T find(Integer id) throws DbException {
        String sql = getReadQuery(String.valueOf(id));
        T findObject;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            List<T> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new DbException("Ошибка на сервере(неудачный поиск в БД)");
            }
            findObject = list.iterator().next();
        } catch (SQLException e) {
            throw new DbException(e);
        }
        return findObject;
    }

    @Override
    public void save(T object) throws DbException {
        if (object.getId() != null) {
            throw new DbException("Обьект уже был сохранен(ошибка на сервере).");
        }

        String sql = getCreateQuery();

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();

            if (count != 1) {
                throw new DbException("Вставлено более одной записи(ошибка на сервере): " + count);
            }

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                object.setId(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DbException(e);
        }
    }

    @Override
    public void update(T object) throws DbException {
        String sql = getUpdateQuery();

        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            prepareStatementForUpdate(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DbException("Update изменил более 1 записи : " + count);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    @Override
    public void delete(Integer id) throws DbException {
        String sql = getDeleteQuery();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, id);
            } catch (Exception e) {
                throw new DbException(e);
            }
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DbException("Delete изменил более 1 записи : " + count);
            }
            statement.close();
        } catch (Exception e) {
            throw new DbException(e);
        }
    }


}
