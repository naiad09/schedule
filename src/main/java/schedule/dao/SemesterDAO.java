package schedule.dao;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import schedule.domain.curriculum.Semester;


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
	
	public Semester get(Integer key) {
		return currentSession().get(daoType, key);
	}
	
}