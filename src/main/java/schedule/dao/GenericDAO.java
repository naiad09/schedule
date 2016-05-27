package schedule.dao;

public class GenericDAO<E> extends MinimalGenericDAO<E> {
	
	public GenericDAO(Class<E> type) {
		super(type);
	}
	
	public void saveOrUpdate(E entity) {
		currentSession().saveOrUpdate(entity);
	}
	
	public void delete(E entity) {
		currentSession().delete(entity);
	}
}