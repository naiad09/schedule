package schedule.dao;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import schedule.domain.struct.Chair;
import schedule.domain.struct.Chair.Faculty;


@Repository
public class ChairDAO extends MinimalGenericDAO<Chair, Integer> {
	
	public ChairDAO() {
		super(Chair.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Chair> findAllByFaculty(Faculty faculty) {
		return getCriteriaDaoType().add(Restrictions.eq("faculty", faculty))
				.list();
	}
	
	public Chair findFull(String shortName) {
		return (Chair) getCriteriaDaoType()
				.setFetchMode("lecturerJobs", FetchMode.JOIN)
				.setFetchMode("skillProfiles", FetchMode.JOIN)
				.add(Restrictions.eq("shortNameEng", shortName)).uniqueResult();
	}
	
}
