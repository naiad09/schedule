package schedule.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import schedule.domain.schedule.Semester;


@Repository
public class SemesterDAO extends GenericDAO<Semester, Integer> {
	
	public SemesterDAO() {
		super(Semester.class);
	}
	
	public Semester getCurrent() {
		
		Semester currentSemester = Semester.getCurrentSemester();
		
		return (Semester) getCriteriaDaoType()
				.add(Restrictions.eq("semesterYear",
						currentSemester.getSemesterYear()))
				.add(Restrictions.eq("fallSpring",
						currentSemester.isFallSpring()))
				.uniqueResult();
	}
	
}