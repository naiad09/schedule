package schedule.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import schedule.dao.util.ConflictFinder;
import schedule.dao.util.ConjuntionExpression;
import schedule.domain.schedule.Conflict;
import schedule.domain.schedule.ScheduleDiscipline.LessonType;
import schedule.domain.schedule.ScheduleItem;
import schedule.domain.semester.Semester;
import schedule.domain.struct.Chair;


@Repository
public class ConflictDAO extends MinimalGenericDAO<Conflict> {
	public ConflictDAO() {
		super(Conflict.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Conflict> findConflicts(ConflictFinder conflictFinder) {
		DetachedCriteria dcSemester = DetachedCriteria.forClass(Semester.class)
				.add(Restrictions.idEq(conflictFinder.getId())).createCriteria("eduProcGraphics")
				.createCriteria("schedules").createCriteria("scheduleDisciplines")
				.createCriteria("scheduleItems").setProjection(Projections.id());
		Criteria crit = getCriteriaDaoType()
				.add(Subqueries.propertyIn("conflictId.schiFrom", dcSemester));
		if (conflictFinder.isClassroomConflict() != conflictFinder.isLecturerConflict()) {
			if (conflictFinder.isClassroomConflict())
				crit.add(Restrictions.eq("classroomConflict", true));
			if (conflictFinder.isLecturerConflict())
				crit.add(Restrictions.eq("lecturerConflict", true));
		}
		if (conflictFinder.getFaculty() != null) {
			DetachedCriteria dcFaculty = DetachedCriteria.forClass(Chair.class)
					.add(Restrictions.eq("faculty", conflictFinder.getFaculty()))
					.createCriteria("skillProfiles").createCriteria("curriculums")
					.createCriteria("groups").createCriteria("schedules")
					.createCriteria("scheduleDisciplines").createCriteria("scheduleItems")
					.setProjection(Projections.id());
			crit.add(Restrictions.or(Subqueries.propertyIn("conflictId.schiFrom", dcFaculty),
					Subqueries.propertyIn("conflictId.schiTo", dcFaculty)));
		}
		
		return crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getConflictingClassrooms(ScheduleItem schi, Integer idSemester) {
		Criteria crit = currentSession().createCriteria(Semester.class)
				.add(Restrictions.idEq(idSemester)).createCriteria("eduProcGraphics")
				.createCriteria("schedules").createCriteria("scheduleDisciplines", "sd")
				.createCriteria("scheduleItems");
		if (schi.getIdScheduleItem() != null)
			crit.add(Restrictions.not(Restrictions.idEq(schi.getIdScheduleItem())));
		crit.add(Restrictions.eq("weekday", schi.getWeekday()))
				.add(new ConjuntionExpression("weekplan", schi.getWeekplan()));
		
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.eq("twain.idTwain", schi.getTwain().getIdTwain()));
		if (schi.getScheduleDiscipline().getLessonType() == LessonType.lab4) {
			disjunction.add(Restrictions.eq("twain.idTwain", schi.getTwain().getIdTwain() + 1));
		}
		disjunction.add(
				Restrictions.and(Restrictions.eq("twain.idTwain", schi.getTwain().getIdTwain() - 1),
						Restrictions.eq("sd.lessonType", LessonType.lab4)));
		
		crit.add(disjunction);
		
		return crit.createCriteria("classrooms").setProjection(Projections.id())
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}
}
