package database.jpa_entity_manager.IDao;

import database.entity.Country;
import database.jdbc_template.abstractDao.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountryDao extends GenericDao<Country> {

}
