package schedule.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import schedule.domain.curriculum.Semester;
import schedule.domain.struct.Enrollment;


@Repository
public class EnrollmentDAO extends GenericDAO<Enrollment> {
	
	public EnrollmentDAO() {
		super(Enrollment.class);
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
