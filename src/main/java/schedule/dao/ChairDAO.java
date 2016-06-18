package schedule.dao;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import schedule.domain.struct.Chair;
import schedule.domain.struct.Chair.Faculty;


/**
 * DAO кафедры, наследует {@link MinimalGenericDAO}. Переопределяет метод
 * выборки всех групп, устанавливая сортировку, и добавляет публичные методы
 * выборки всех кафедр факультета и выборки кафедры по ее сокращению на
 * латинице;
 */
@Repository
public class ChairDAO extends MinimalGenericDAO<Chair> {
	public ChairDAO() {
		super(Chair.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Chair> getAll() {
		return getCriteriaDaoType().addOrder(Order.asc("faculty")).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Chair> findAllByFaculty(Faculty faculty) {
		return getCriteriaDaoType().add(Restrictions.eq("faculty", faculty)).list();
	}
	
	public Chair findFull(String shortName) {
		return (Chair) getCriteriaDaoType().add(Restrictions.eq("shortNameEng", shortName))
				.uniqueResult();
	}
	
}
