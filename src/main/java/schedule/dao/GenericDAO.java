package schedule.dao;

import java.io.Serializable;
import java.util.List;

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
	
	public Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void create(E entity) {
		currentSession().save(entity);
	}
	
	public E read(K key) {
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
		return currentSession().createCriteria(daoType).list();
	}
}