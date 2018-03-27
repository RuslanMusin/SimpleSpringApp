package database.dao.IDao;

import database.dao.abstractDao.GenericDao;
import database.entity.IMessage;
import database.entity.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageDao extends GenericDao<IMessage> {
}
