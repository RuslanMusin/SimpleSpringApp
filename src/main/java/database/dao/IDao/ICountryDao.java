package database.dao.IDao;

import database.dao.abstractDao.GenericDao;
import database.entity.Country;
import database.exceptions.DbException;


import java.util.List;

public interface ICountryDao extends GenericDao<Country> {

    String getSelectCountriesByName(String queryCountry);

    String getSelectCountriesByMovie(Integer movieId);

    List<Country> findCountriesByName(String queryCountry) throws DbException;

    public List<Country> findAllCountriesByMovie(Integer movieId) throws DbException ;
}
