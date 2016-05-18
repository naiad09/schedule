package schedule.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class GenericDAO<E, K extends Serializable> {
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Class<E> daoType;
	
	public GenericDAO() {}
	
	public GenericDAO(Class<E> type) {
		this.daoType = type;
	}
	
	public void create(E entity) {
		currentSession().save(entity);
	}
	
	public E find(K key) {
		return currentSession().get(daoType, key);
	}
	
	public void update(E entity) {
		currentSession().update(entity);
	}
	
	public void delete(E entity) {
		currentSession().delete(entity);
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