package schedule.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * Используется, чтобы получить идентифиатор последней вставленной в БД строки.
 */
@Repository
public class LastInsertedIdDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public Object getLastInsertedId() {
		return sessionFactory.getCurrentSession()
				.createSQLQuery("select LAST_INSERT_ID()").uniqueResult();
	}
}
