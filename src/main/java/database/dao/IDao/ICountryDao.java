package database.dao.IDao;

import database.dao.abstractDao.GenericDao;
import database.entity.Country;
import database.exceptions.DbException;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ICountryDao extends GenericDao<Country> {

    /*String getSelectCountriesByName(String queryCountry);

    String getSelectCountriesByMovie(Integer movieId);

    List<Country> findCountriesByName(String queryCountry) throws DbException;

    List<Country> findAllCountriesByMovie(Integer movieId) throws DbException ;*/
}
