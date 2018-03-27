package database.dao.postgresDao;

import database.dao.IDao.ICountryDao;
import database.dao.abstractDao.AbstractDao;
import database.entity.Country;
import database.exceptions.DbException;
import utils.Const;
import utils.DbWrapper;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class CountryDao extends AbstractDao<Country> implements ICountryDao{

    private final String TABLE_NAME = Const.DATABASE + "." +
                                        Const.SCHEMA + "." + "countries";

    private final String COL_ID = "country_id";

    private final String COL_NAME = "name";

    public CountryDao(Connection connection) {
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

    public String getSelectCountriesByName(String queryCountry){
        queryCountry = queryCountry.toLowerCase();
        return getSelectQuery() + " WHERE LOWER(" + COL_NAME + ") SIMILAR TO " + "'" +  queryCountry + "%'"
                + " ORDER BY " + COL_NAME + " DESC LIMIT 10;";
    }

    public String getSelectCountriesByMovie(Integer movieId){
        return "SELECT * FROM getmoviecountries(" + movieId + ")";
    }


    @Override
    protected List<Country> parseResultSet(ResultSet rs) throws DbException {
        LinkedList<Country> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getInt(COL_ID));
                country.setName(rs.getString(COL_NAME));
                result.add(country);
            }
        } catch (Exception e) {
            throw new DbException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Country country) throws DbException {
        try {
            int i = 1;
            statement.setString(i, country.getName());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Country country) throws DbException {
        try {
            int i = 1;
            statement.setString(i++, country.getName());
            statement.setInt(i, country.getId());
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    public List<Country> findCountriesByName(String queryCountry) throws DbException {
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
    }
}
