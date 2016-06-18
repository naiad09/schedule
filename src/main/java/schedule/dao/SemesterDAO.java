package schedule.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import schedule.domain.curriculum.Semester;
import schedule.domain.struct.Enrollment;


/**
 * DAO семестра, наследует GenericDAO. Переопределяет метод загрузки всех
 * семестров, добавляя сортировку. Позволяет выбрать экземпляр семестра, который
 * является текущим на данный момент, а также все наборы, которые обучаются в
 * конкретном семестре.
 */
@Repository
public class SemesterDAO extends GenericDAO<Semester> {
	
	public SemesterDAO() {
		super(Semester.class);
	}
	
	public Semester getCurrent() {
		Semester currentSemester = Semester.getCurrentSemester();
		
		return (Semester) getCriteriaDaoType()
				.add(Restrictions.eq("semesterYear", currentSemester.getSemesterYear()))
				.add(Restrictions.eq("fallSpring", currentSemester.getFallSpring())).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Semester> getAll() {
		return getCriteriaDaoType().addOrder(Order.desc("semesterYear"))
				.addOrder(Order.desc("fallSpring")).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Enrollment> trainingInSemester(Semester semester) {
		List<Enrollment> list = getCriteriaDaoType()
				.add(Restrictions
						.sqlRestriction("year_start+period_years+ IF(period_months>0,0.5,0) > "
								+ semester.getSemesterYear() + "+IF(" + semester.getFallSpring()
								+ ",0.5,0) and year_start <= " + semester.getSemesterYear()))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		System.out.println(list);
		return list.stream().filter(c -> c.getCommonCurriculums().size() > 0)
				.collect(Collectors.toList());
	}
}