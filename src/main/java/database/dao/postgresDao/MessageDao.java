package database.dao.postgresDao;

import database.dao.IDao.IMessageDao;
import database.dao.abstractDao.AbstractDao;
import database.entity.IMessage;
import database.entity.Message;
import database.entity.Right;
import database.exceptions.DbException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import utils.Const;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class MessageDao extends AbstractDao<IMessage> implements IMessageDao {

    private String TABLE_NAME = Const.DATABASE + "." +
            Const.SCHEMA + "." + "messages";

    private final String COL_ID = "message_id";
    private final String COL_MESSAGE = "message_content";

    public MessageDao(){}

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
    public String getReadQuery() {

        return getSelectQuery() + " WHERE " + COL_ID + " = ? ;";
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
    protected RowMapper<IMessage> getRowMapper() {
        return new RowMapper<IMessage>() {

            @Override
            public IMessage mapRow(ResultSet rs, int i) throws SQLException {
                Message message = new Message();
                message.setId(rs.getInt(COL_ID));
                message.setMessage(rs.getString(COL_MESSAGE));

                return message;
            }
        };
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement,
                                             IMessage right) throws SQLException {

            int i = 1;
            statement.setString(i, right.getMessage());

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement,
                                             IMessage right) throws SQLException {
            int i = 1;
            statement.setString(i, right.getMessage());
    }
}
