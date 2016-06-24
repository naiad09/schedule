package schedule.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import schedule.dao.util.ConflictFinder;
import schedule.domain.schedule.Conflict;
import schedule.domain.semester.Semester;
import schedule.domain.struct.Chair;


/**
 * DAO кафедры, наследует {@link MinimalGenericDAO}. Переопределяет метод
 * выборки всех групп, устанавливая сортировку, и добавляет публичные методы
 * выборки всех кафедр факультета и выборки кафедры по ее сокращению на
 * латинице;
 */
@Repository
public class ConflictDAO extends MinimalGenericDAO<Conflict> {
	public ConflictDAO() {
		super(Conflict.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Conflict> getBySemester(ConflictFinder conflictFinder) {
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
	
}
