package database.jpa_entity_manager;

import database.entity.Country;
import database.entity.Genre;
import database.jpa_entity_manager.IDao.IGenreDao;
import database.jpa_entity_manager.abstractDao.AbstractDao;

public class GenreJpaDao extends AbstractDao<Genre> implements IGenreDao {

    @Override
    protected String getAllQuery() {
        return "from Genre";
    }

    @Override
    protected Class<Genre> getEntityClass() {
        return Genre.class;
    }
}
