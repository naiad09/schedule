package schedule.dao;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import schedule.domain.schedule.Schedule;


@Repository
public class ScheduleDAO extends GenericDAO<Schedule> {
	
	public ScheduleDAO() {
		super(Schedule.class);
	}
	
	public boolean isExists(Schedule schedule) {
		Long u = (Long) getCriteriaDaoType()
				.add(Restrictions.eq("group", schedule.getGroup()))
				.add(Restrictions.eq("termNum", schedule.getTermNum()))
				.setProjection(Projections.rowCount()).uniqueResult();
		
		return u == 0 ? false : true;
	}
	
}