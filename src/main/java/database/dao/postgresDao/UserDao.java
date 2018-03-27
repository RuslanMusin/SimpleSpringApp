package database.dao.postgresDao;

import database.dao.IDao.IUserDao;
import database.dao.abstractDao.AbstractDao;
import database.entity.Country;
import database.entity.Right;
import database.entity.User;
import database.exceptions.DbException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import utils.Const;
import utils.DbWrapper;


import javax.sql.DataSource;
import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class UserDao extends AbstractDao<User> implements IUserDao {

    private final String TABLE_NAME = Const.DATABASE + "." +
            Const.SCHEMA + "." + "users";

    private final String FULL_USER_INF_VIEW = "full_user_inf_view";

    private final String COL_ID = "user_id";

    private final String COL_EMAIL = "email";

    private final String COL_PASSWORD = "password";

    private final String COL_USERNAME = "username";

    private final String COL_COUNTRY_ID = "country_id";

    private final String COL_GENDER = "gender";

    private final String COL_RIGHT_ID = "user_right";

    private final String COL_COOKIE_ID = "cookie_id";

    //доп.поле
    private final String COL_COUNTRY = "name";
    private final String COL_RIGHT = "right_name";

    @Override
    public String getSelectQuery() {

        return "SELECT * FROM " + FULL_USER_INF_VIEW;
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO " + TABLE_NAME + " ("
                + COL_EMAIL + ","
                + COL_PASSWORD + ","
                + COL_USERNAME + ","
                + COL_COUNTRY_ID + ","
                + COL_GENDER + ","
                + COL_COOKIE_ID
                + ") VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getReadQuery() {

        return getSelectQuery() + " WHERE " + COL_ID + " = ? ;";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE " + TABLE_NAME + " SET "
                + COL_USERNAME + " = ?, "
                + COL_COUNTRY_ID + " = ?, "
                + COL_GENDER + " =  ?, "
                + COL_RIGHT_ID + " = ? "
                + " WHERE " + COL_ID + " = ?;";
    }

    @Override
    public String getDeleteQuery() {

        return "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?;";
    }

    public String getSelectByCookieId(){
        return getSelectQuery() +  " WHERE " + COL_COOKIE_ID + " = ?;" ;
    }

    public String getSelectByEmail() {
        return getSelectQuery() + " WHERE " + COL_EMAIL + " = ?;";
    }

    public String getSelectCookieId(String cookieId){
        return "SELECT *" + " FROM " + TABLE_NAME +" WHERE " + COL_COOKIE_ID + " = '" + cookieId + "';" ;
    }

    public String getSelectPassword(){
        return "SELECT " + COL_PASSWORD + " FROM " + TABLE_NAME
                + " WHERE " + COL_EMAIL + " = ?;";
    }

    public String getSelectDoubleEmail(String email){

        return "SELECT *" + " FROM " + TABLE_NAME +" WHERE " + COL_EMAIL + " = '" + email + "';" ;
    }

    @Override
    protected RowMapper<User> getRowMapper() {
        return new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setId(rs.getInt(COL_ID));
                user.setEmail(rs.getString(COL_EMAIL));
                user.setUsername(rs.getString(COL_USERNAME));
                user.setCountry(new Country(rs.getInt(COL_COUNTRY_ID),rs.getString(COL_COUNTRY)));
                user.setGender(rs.getString(COL_GENDER));
                user.setRights(new Right(rs.getInt(COL_RIGHT_ID),rs.getString(COL_RIGHT)));
                user.setCookieId(rs.getString(COL_COOKIE_ID));

                return user;
            }
        };
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User user) throws SQLException {
        int i = 1;
        statement.setString(i++, user.getEmail());
        statement.setString(i++, user.getPassword());
        statement.setString(i++, user.getUsername());
        statement.setInt(i++, user.getCountry().getId());
        statement.setString(i++, user.getGender());
        statement.setString(i, user.getCookieId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User user) throws SQLException {
        int i = 1;
        statement.setString(i++, user.getUsername());
        statement.setInt(i++, user.getCountry().getId());
        statement.setString(i++, user.getGender());
        statement.setInt(i++, user.getRights().getId());
        statement.setInt(i, user.getId());
    }

    public User findByEmail(String email) throws DbException {
        String sql = getSelectByEmail();
        User user = jdbcTemplateObject.queryForObject(sql,
                new Object[]{email},getRowMapper());
        /*try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            statement.setString(1,email);
            ResultSet rs = statement.executeQuery();
            List<User> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new DbException("findByEmailExПользователь не найден по email.");
            }
            user = list.iterator().next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DbException(e);
        }*/
        return user;
    }

    /*public User findByCookieId(String cookieId) throws DbException {
        String sql = getSelectByCookieId();
        User user;
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            statement.setString(1,cookieId);
            ResultSet rs = statement.executeQuery();
            List<User> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new DbException("findByCookieIdEx");
            }
            user = list.iterator().next();
        } catch (SQLException e) {
            throw new DbException(e);
        }
        return user;
    }*/

   /* public boolean findCookieIdMatch(String cookieId) throws DbException {
        String sql = getSelectCookieId(cookieId);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
        return false;
    }

    public boolean findDoubleEmail(String email) throws DbException {
        String sql = getSelectDoubleEmail(email);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new DbException(e);
        }
        return false;
    }*/

    /*public String findPassword(String email) throws DbException {
        String password = null;
        String sql = getSelectPassword();
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            statement.setString(1,email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                password = rs.getString(COL_PASSWORD);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return password;
    }*/
}
