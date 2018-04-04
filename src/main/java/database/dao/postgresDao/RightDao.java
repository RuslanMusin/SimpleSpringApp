package database.dao.postgresDao;

import database.dao.IDao.IRightDao;
import database.dao.abstractDao.AbstractDao;
import database.entity.Right;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import utils.Const;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RightDao extends AbstractDao<Right> implements IRightDao{

    private final String TABLE_NAME = Const.DATABASE + "." +
            Const.SCHEMA + "." + "label.rights";

    private final String COL_ID = "right_id";

    private final String COL_NAME = "right_name";

    @Override
    public String getSelectQuery() {
        return "SELECT "
                + COL_ID + ","
                + COL_NAME
                + " FROM " + TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO " + TABLE_NAME + " ("
                + COL_NAME
                + ") VALUES (?);";
    }

    @Override
    public String getReadQuery() {

        return getSelectQuery() + " WHERE " + COL_ID + " = ? ;";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE " + TABLE_NAME + " SET "
                + COL_NAME + " = ? "
                + " WHERE " + COL_ID + " = ?;";
    }

    @Override
    public String getDeleteQuery() {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?;";
    }

    @Override
    protected RowMapper<Right> getRowMapper() {
        return new RowMapper<Right>() {

            @Override
            public Right mapRow(ResultSet rs, int i) throws SQLException {
                Right right = new Right();
                right.setId(rs.getInt(COL_ID));
                right.setName(rs.getString(COL_NAME));

                return right;
            }
        };
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement,
                                             Right right) throws SQLException {
            int i = 1;
            statement.setString(i, right.getName());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement,
                                             Right right) throws SQLException {
            int i = 1;
            statement.setString(i, right.getName());
    }
}
