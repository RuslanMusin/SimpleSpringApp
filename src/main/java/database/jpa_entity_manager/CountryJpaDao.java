package database.jpa_entity_manager;

import database.entity.Country;
import database.jpa_entity_manager.IDao.ICountryDao;
import database.jpa_entity_manager.abstractDao.AbstractDao;
import org.springframework.stereotype.Repository;
import utils.exceptions.DbException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CountryJpaDao extends AbstractDao<Country> implements ICountryDao{

    @Override
    protected String getAllQuery() {
        return "from Country";
    }

    @Override
    protected Class<Country> getEntityClass() {
        return Country.class;
    }
}
