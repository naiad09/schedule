package schedule.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import schedule.domain.schedule.Semester;
import schedule.domain.struct.Enrollment;


@Repository
public class EnrollmentDAO extends GenericDAO<Enrollment> {
	
	public EnrollmentDAO() {
		super(Enrollment.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Enrollment> trainingInSemester(Semester semester) {
		return getCriteriaDaoType().add(Restrictions.sqlRestriction(
				"year_start+period_years+ IF(period_months>0,0.5,0) > "
						+ semester.getSemesterYear() + "+IF("
						+ semester.getFallSpring()
						+ ",0.5,0) and year_start <= "
						+ semester.getSemesterYear()))
				.list();
	}
}
