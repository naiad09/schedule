package schedule.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import schedule.domain.curriculum.Curriculum;
import schedule.domain.curriculum.ProfileDiscipline;
import schedule.domain.curriculum.Semester;
import schedule.domain.persons.Group;
import schedule.domain.schedule.EduProcGraphic;
import schedule.domain.schedule.Schedule;
import schedule.domain.schedule.ScheduleDiscipline;
import schedule.domain.schedule.ScheduleDiscipline.LessonType;
import schedule.domain.schedule.ScheduleItem;
import schedule.web.RawSchedule;


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
		
		List<ScheduleDiscipline> groupLessonTypes = new ArrayList<>();
		
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
								groupLessonTypes.add(new ScheduleDiscipline(
										schedule, dt, LessonType.lec,
										profileDiscipline.getDiscipline()));
							if (cd.getLabHours() > 0) {
								groupLessonTypes.add(new ScheduleDiscipline(
										schedule, dt, LessonType.lab,
										profileDiscipline.getDiscipline()));
								groupLessonTypes.add(new ScheduleDiscipline(
										schedule, dt, LessonType.lab4,
										profileDiscipline.getDiscipline()));
							}
							if (cd.getSeminarHours() > 0)
								groupLessonTypes.add(new ScheduleDiscipline(
										schedule, dt, LessonType.pract,
										profileDiscipline.getDiscipline()));
						}));
		
		schedule.setScheduleDisciplines(groupLessonTypes);
		
		currentSession().save(schedule);
	}
	
	public List<Integer> getConflictingClassrooms(ScheduleItem scheduleItem) {
		// TODO
		return null;
	}
	
	// если реализовывать оповещения о смене расписания, то тут можно искать
	// изменившиеся части
	public void update(Schedule schedule, RawSchedule rawSchedule) {
		
		mergeFromRawSchedule(schedule, rawSchedule);
		
		// а тут поиск конфликтов должен быть
		
		currentSession().update(schedule);
	}
	
	private void mergeFromRawSchedule(Schedule schedule,
			RawSchedule rawSchedule) {
		schedule.getScheduleDisciplines().stream().forEach(oldSD -> {
			ScheduleDiscipline newSD = rawSchedule.getScheduleDisciplines()
					.stream()
					.filter(newSD1 -> newSD1.getIdScheduleDiscipline() == oldSD
							.getIdScheduleDiscipline())
					.findAny().get();
			oldSD.getScheduleItems().stream().filter(oldSchi -> !newSD
					.getScheduleItems().stream()
					.filter(newSchi -> newSchi.getIdScheduleItem() == oldSchi
							.getIdScheduleItem())
					.findAny().isPresent())
					.forEach(oldSchi -> currentSession().delete(oldSchi));
		});
		
		schedule.setScheduleDisciplines(rawSchedule.getScheduleDisciplines());
	}
}