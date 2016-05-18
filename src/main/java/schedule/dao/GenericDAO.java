package schedule.dao;

import java.io.Serializable;


public class GenericDAO<E, K extends Serializable>
		extends MinimalGenericDAO<E, K> {
	
	public GenericDAO(Class<E> type) {
		super(type);
	}
	
	public void create(E entity) {
		currentSession().save(entity);
	}
	
	public E get(K key) {
		return currentSession().get(daoType, key);
	}
	
	public void update(E entity) {
		currentSession().update(entity);
	}
	
	public void delete(E entity) {
		currentSession().delete(entity);
	}
}