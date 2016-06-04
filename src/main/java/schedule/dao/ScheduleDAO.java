package schedule.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import schedule.domain.persons.Group;
import schedule.domain.schedule.GroupLessonType;
import schedule.domain.schedule.GroupLessonType.LessonType;
import schedule.domain.schedule.ProfileDiscipline;
import schedule.domain.schedule.Schedule;
import schedule.domain.schedule.Semester;
import schedule.domain.struct.Curriculum;
import schedule.domain.struct.EduProcGraphic;


@Repository
public class ScheduleDAO extends GenericDAO<Schedule> {
	
	public ScheduleDAO() {
		super(Schedule.class);
	}
	
	public boolean isExists(Schedule schedule) {
		Long u = (Long) getCriteriaDaoType()
				.add(Restrictions.eq("group", schedule.getGroup()))
				.add(Restrictions.eq("eduProcGraphic",
						schedule.getEduProcGraphic()))
				.setProjection(Projections.rowCount()).uniqueResult();
		
		return u == 0 ? false : true;
	}
	
	public Schedule get(Integer key) {
		return currentSession().get(daoType, key);
	}
	
	// Вычисляем подходящие дисциплины для расписания данной группы
	public void create(Schedule schedule) {
		System.out.println("here");
		// рефреш
		Group group = schedule.getGroup();
		currentSession().refresh(group);
		EduProcGraphic eduProcGraphic = schedule.getEduProcGraphic();
		currentSession().refresh(eduProcGraphic);
		
		Semester semester = eduProcGraphic.getSemester();
		Curriculum curriculum = group.getCurriculum();
		
		// номер семестра
		int termNum = (semester.getSemesterYear() - curriculum
				.getCommonCurriculum().getEnrollment().getYearStart()) * 2
				+ (semester.getFallSpring() ? 2 : 1);
		
		List<GroupLessonType> groupLessonTypes = new ArrayList<>();
		
		curriculum.getCommonCurriculum().getCommonDisciplines()
				.forEach(cd -> cd.getDiscTerms().stream()
						.filter(dt -> dt.getTermNum() == termNum)
						// фильтруем по номеру семестра
						.forEach(dt -> {
							List<ProfileDiscipline> pd = cd
									.getProfileDisciplines();
							ProfileDiscipline profileDiscipline;
							if (pd.size() == 1) {
								profileDiscipline = pd.get(0);
								// или это общая дисциплина
							} else {// или профильная, тогда выбираем для
									// конкретного профиля
								profileDiscipline = pd.stream()
										.filter(r -> r
												.getCurriculum() == curriculum
														.getIdCurriculum())
										.findAny().get();
							}
							if (cd.getLectureHours() > 0)// и создаем нужные
								groupLessonTypes.add(new GroupLessonType(
										schedule, dt, LessonType.lec,
										profileDiscipline.getDiscipline()));
							if (cd.getLabHours() > 0) {
								groupLessonTypes.add(new GroupLessonType(
										schedule, dt, LessonType.lab,
										profileDiscipline.getDiscipline()));
								groupLessonTypes.add(new GroupLessonType(
										schedule, dt, LessonType.lab4,
										profileDiscipline.getDiscipline()));
							}
							if (cd.getSeminarHours() > 0)
								groupLessonTypes.add(new GroupLessonType(
										schedule, dt, LessonType.pract,
										profileDiscipline.getDiscipline()));
						}));
		
		schedule.setGroupLessonTypes(groupLessonTypes);
		
		currentSession().save(schedule);
	}
	
}