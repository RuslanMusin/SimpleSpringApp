package database.dao.postgresDao;

import database.dao.IDao.IRightDao;
import database.dao.abstractDao.AbstractDao;
import database.entity.Right;

import database.exceptions.DbException;
import utils.Const;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class RightDao extends AbstractDao<Right> implements IRightDao{

    private final String TABLE_NAME = Const.DATABASE + "." +
            Const.SCHEMA + "." + "label.rights";

    private final String COL_ID = "right_id";

    private final String COL_NAME = "right_name";

    public RightDao(Connection connection) {
        super(connection);
    }

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
    public String getReadQuery(String id) {

        return getSelectQuery() + " WHERE " + COL_ID + " = " + id + ";";
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
    protected List<Right> parseResultSet(ResultSet rs) throws DbException {
        LinkedList<Right> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Right right = new Right();
                right.setId(rs.getInt(COL_ID));
                right.setName(rs.getString(COL_NAME));
                result.add(right);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Right right) throws DbException {
        try {
            int i = 1;
            statement.setString(i, right.getName());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Right right) throws DbException {
        try {
            int i = 1;
            statement.setString(i, right.getName());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }
}
