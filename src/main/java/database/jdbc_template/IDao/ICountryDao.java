package database.jdbc_template.IDao;

import database.jdbc_template.abstractDao.GenericDao;
import database.entity.Country;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountryDao extends GenericDao<Country> {

    /*String getSelectCountriesByName(String queryCountry);

    String getSelectCountriesByMovie(Integer movieId);

    List<Country> findCountriesByName(String queryCountry) throws DbException;

    List<Country> findAllCountriesByMovie(Integer movieId) throws DbException ;*/
}
