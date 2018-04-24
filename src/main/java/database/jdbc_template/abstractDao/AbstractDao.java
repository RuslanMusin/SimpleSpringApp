package database.jdbc_template.abstractDao;

import database.entity.Identified;
import utils.exceptions.DbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public abstract class AbstractDao<T extends Identified> implements GenericDao<T> {

    protected JdbcTemplate jdbcTemplateObject;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public AbstractDao() {
    }

    public abstract String getSelectQuery();

    public abstract String getCreateQuery();

    public abstract String getReadQuery();

    public abstract String getUpdateQuery();

    public abstract String getDeleteQuery();

    protected abstract RowMapper<T> getRowMapper();

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws SQLException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws SQLException;

    @Override
    public List<T> findAll() throws DbException {
        String sql = getSelectQuery();
        List<T> list = jdbcTemplateObject.query(sql,getRowMapper());

        return list;
    }

    public T find(Integer id) throws DbException {
        String sql = getReadQuery();
        T findObject = jdbcTemplateObject.queryForObject(sql,
                                            new Object[]{id},getRowMapper());
        return findObject;
    }

    @Override
    public void save(final T object) throws DbException {
        String sql = getCreateQuery();
        jdbcTemplateObject.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                prepareStatementForInsert(preparedStatement,object);
            }
        });
    }

    @Override
    public void update(final T object) throws DbException {
        String sql = getUpdateQuery();
        jdbcTemplateObject.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                prepareStatementForUpdate(preparedStatement,object);
            }
        });
    }

    @Override
    public void delete(Integer id) throws DbException {
        String sql = getDeleteQuery();
        jdbcTemplateObject.update(sql,id);
    }
}
