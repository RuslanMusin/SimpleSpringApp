package database.dao.postgresDao;

import database.dao.IDao.ICountryDao;
import database.dao.abstractDao.AbstractDao;
import database.entity.Country;
import database.entity.Right;
import database.exceptions.DbException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import utils.Const;
import utils.DbWrapper;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class CountryDao extends AbstractDao<Country> implements ICountryDao{

    private final String TABLE_NAME = Const.DATABASE + "." +
                                        Const.SCHEMA + "." + "countries";

    private final String COL_ID = "country_id";

    private final String COL_NAME = "name";

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

    public String getSelectCountriesByName(String queryCountry){
        queryCountry = queryCountry.toLowerCase();
        return getSelectQuery() + " WHERE LOWER(" + COL_NAME + ") SIMILAR TO " + "'" +  queryCountry + "%'"
                + " ORDER BY " + COL_NAME + " DESC LIMIT 10;";
    }

    public String getSelectCountriesByMovie(Integer movieId){
        return "SELECT * FROM getmoviecountries(" + movieId + ")";
    }

    @Override
    protected RowMapper<Country> getRowMapper() {
        return new RowMapper<Country>() {

            @Override
            public Country mapRow(ResultSet rs, int i) throws SQLException {
                Country country = new Country();
                country.setId(rs.getInt(COL_ID));
                country.setName(rs.getString(COL_NAME));

                return country;
            }
        };
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Country country) throws SQLException {
        int i = 1;
        statement.setString(i, country.getName());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Country country) throws SQLException {
        int i = 1;
        statement.setString(i++, country.getName());
        statement.setInt(i, country.getId());
    }

   /* public List<Country> findCountriesByName(String queryCountry) throws DbException {
        List<Country> list;
        String sql = getSelectCountriesByName(queryCountry);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }

    public List<Country> findAllCountriesByMovie(Integer movieId) throws DbException {
        List<Country> list;
        String sql = getSelectCountriesByMovie(movieId);
        try (PreparedStatement statement = DbWrapper.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return list;
    }*/
}
