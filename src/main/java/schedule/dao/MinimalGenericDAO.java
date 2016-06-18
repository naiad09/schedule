package schedule.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * Параметризированный DAO для классов модели, не подлежащих редактированию.
 * Содержит наследуемые методы доступа к Hibernate-сессии и публичные методы
 * выборки всех экземпляров сущности и одного экземпляра по id.
 */
@Transactional
public class MinimalGenericDAO<E> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected final Class<E> daoType;
	
	public MinimalGenericDAO(Class<E> type) {
		this.daoType = type;
	}
	
	public E get(Integer key) {
		return currentSession().get(daoType, key);
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