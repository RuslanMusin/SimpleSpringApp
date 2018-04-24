package database.jpa_entity_manager;
import database.entity.Book;
import database.jpa_entity_manager.IDao.IMovieDao;
import database.jpa_entity_manager.abstractDao.AbstractDao;

public class MovieJpaDao extends AbstractDao<Book> implements IMovieDao{

    @Override
    protected String getAllQuery() {
        return "from Book";
    }

    @Override
    protected Class<Book> getEntityClass() {
        return Book.class;
    }
}
