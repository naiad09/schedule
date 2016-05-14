package schedule.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class SimpleGenericDAO<E> extends GenericDAO<E, Integer> {
	
	public SimpleGenericDAO() {}
	
	public SimpleGenericDAO(Class<E> type) {
		super(type);
	}
	
}
