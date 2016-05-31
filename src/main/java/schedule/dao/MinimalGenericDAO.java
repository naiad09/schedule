package schedule.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class MinimalGenericDAO<E> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected final Class<E> daoType;
	
	public MinimalGenericDAO(Class<E> type) {
		this.daoType = type;
	}
	
	@SuppressWarnings("unchecked")
	public List<E> getAll() {
		return getCriteriaDaoType().list();
	}
	
	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected Criteria getCriteriaDaoType() {
		return currentSession().createCriteria(daoType);
	}
	
}