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
	
	public void add(E entity) {
		currentSession().save(entity);
	}
	
	public void saveOrUpdate(E entity) {
		currentSession().saveOrUpdate(entity);
	}
	
	public void remove(E entity) {
		currentSession().delete(entity);
	}
	
	public E find(K key) {
		return currentSession().get(daoType, key);
	}
	
	@SuppressWarnings("unchecked")
	public List<E> getAll() {
		return currentSession().createCriteria(daoType).list();
	}
}