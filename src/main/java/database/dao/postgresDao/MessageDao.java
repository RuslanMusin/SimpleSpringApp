package database.dao.postgresDao;

import database.dao.IDao.IMessageDao;
import database.dao.abstractDao.AbstractDao;
import database.entity.IMessage;
import database.entity.Message;
import database.exceptions.DbException;
import org.springframework.stereotype.Repository;
import utils.Const;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Repository
public class MessageDao extends AbstractDao<IMessage> implements IMessageDao {

    private String TABLE_NAME = Const.DATABASE + "." +
            Const.SCHEMA + "." + "messages";

    private final String COL_ID = "message_id";
    private final String COL_MESSAGE = "message_content";

    public MessageDao(){}

    public MessageDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT "
                + COL_ID + ","
                + COL_MESSAGE
                + " FROM " + TABLE_NAME;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO " + TABLE_NAME + " ("
                + COL_MESSAGE
                + ") VALUES (?);";
    }

    @Override
    public String getReadQuery(String id) {

        return getSelectQuery() + " WHERE " + COL_ID + " = " + id + ";";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE " + TABLE_NAME + " SET "
                + COL_MESSAGE + " = ? "
                + " WHERE " + COL_ID + " = ?;";
    }

    @Override
    public String getDeleteQuery() {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?;";
    }

    @Override
    protected List<IMessage> parseResultSet(ResultSet rs) throws DbException {
        LinkedList<IMessage> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt(COL_ID));
                message.setMessage(rs.getString(COL_MESSAGE));
                result.add(message);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, IMessage right) throws DbException {
        try {
            int i = 1;
            statement.setString(i, right.getMessage());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, IMessage right) throws DbException {
        try {
            int i = 1;
            statement.setString(i, right.getMessage());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }
}
