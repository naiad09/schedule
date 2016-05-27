package schedule.dao;

import org.springframework.stereotype.Repository;

import schedule.domain.struct.Enrollment;


@Repository
public class EnrollmentDAO extends GenericDAO<Enrollment> {
	
	public EnrollmentDAO() {
		super(Enrollment.class);
	}
	
}
