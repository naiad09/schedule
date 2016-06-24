package schedule.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import schedule.dao.util.RawSchedule;
import schedule.domain.curriculum.Curriculum;
import schedule.domain.curriculum.ProfileDiscipline;
import schedule.domain.persons.Group;
import schedule.domain.persons.Lecturer;
import schedule.domain.schedule.Classroom;
import schedule.domain.schedule.Conflict;
import schedule.domain.schedule.ConflictId;
import schedule.domain.schedule.Schedule;
import schedule.domain.schedule.ScheduleDiscipline;
import schedule.domain.schedule.ScheduleDiscipline.LessonType;
import schedule.domain.schedule.ScheduleItem;
import schedule.domain.semester.EduProcGraphic;
import schedule.domain.semester.Semester;


/**
 * DAO расписания, наследует {@link GenericDAO}. Метод создания расписания
 * содержит алгоритм генерации дисциплин расписания по учебному плану. Метод
 * поиска конфликтующих аудиторий производит выборку из базы данных тех
 * аудиторий, которые будут конфликтовать с редактируемым элементом расписания,
 * переданным в качестве параметра. Метод сохранения расписания собирает
 * расписание из класса {@link RawSchedule}, удаляя ненужные более элемента
 * расписания. Метод поиска конфликтов ищет конфликты всех элементов
 * сохраняемого расписания с уже имеющимися в базе элементами расписания: это
 * могут быть конфликты преподавателей или аудиторий, но при этом физкультура и
 * поточные лекции конфликтами не считаются.
 */
@Repository
public class ScheduleDAO extends GenericDAO<Schedule> {
	public ScheduleDAO() {
		super(Schedule.class);
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
		int termNum = (semester.getSemesterYear()
				- curriculum.getCommonCurriculum().getEnrollment().getYearStart()) * 2
				+ (semester.getFallSpring() ? 2 : 1);
		
		List<ScheduleDiscipline> groupLessonTypes = new ArrayList<>();
		
		curriculum.getCommonCurriculum().getCommonDisciplines()
				.forEach(cd -> cd.getDiscTerms().stream().filter(dt -> dt.getTermNum() == termNum)
						// фильтруем по номеру семестра
						.forEach(dt -> {
							List<ProfileDiscipline> pd = cd.getProfileDisciplines();
							ProfileDiscipline profileDiscipline;
							if (pd.size() == 1) {
								profileDiscipline = pd.get(0);
								// или это общая дисциплина
							} else {// или профильная, тогда выбираем для
								// конкретного профиля
								profileDiscipline = pd.stream().filter(
										r -> r.getCurriculum() == curriculum.getIdCurriculum())
										.findAny().get();
							}
							if (cd.getLectureHours() > 0)// и создаем нужные
								groupLessonTypes.add(new ScheduleDiscipline(schedule, dt,
										LessonType.lec, profileDiscipline.getDiscipline()));
							if (cd.getLabHours() > 0) {
								groupLessonTypes.add(new ScheduleDiscipline(schedule, dt,
										LessonType.lab, profileDiscipline.getDiscipline()));
								groupLessonTypes.add(new ScheduleDiscipline(schedule, dt,
										LessonType.lab4, profileDiscipline.getDiscipline()));
							}
							if (cd.getSeminarHours() > 0)
								groupLessonTypes.add(new ScheduleDiscipline(schedule, dt,
										LessonType.pract, profileDiscipline.getDiscipline()));
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
	public void update(Integer idSchedule, RawSchedule rawSchedule) {
		Schedule schedule = get(idSchedule);
		mergeFromRawSchedule(schedule, rawSchedule);
		currentSession().update(schedule);
		searchConflicts(schedule);
		System.err.println("== КОНЕЦ СОХРАНЕНИЯ ==");
	}
	
	private void searchConflicts(Schedule schedule) {
		List<ScheduleItem> listScheduleItems = new ArrayList<ScheduleItem>();
		Semester semester = schedule.getEduProcGraphic().getSemester();
		currentSession().refresh(semester);
		semester.getEduProcGraphics()
				.forEach(ep -> ep.getSchedules().forEach(sc -> sc.getScheduleDisciplines()
						.forEach(sd -> listScheduleItems.addAll(sd.getScheduleItems()))));
		System.err.println("== Конфликты == ");
		schedule.getScheduleDisciplines()
				.forEach(sd -> sd.getScheduleItems().stream().peek(schi1 -> {
					schi1.getConflictsFrom().forEach(conflict -> currentSession().delete(conflict));
					schi1.getConflictsTo().forEach(conflict -> currentSession().delete(conflict));
				}).forEach(schi1 -> {
					listScheduleItems.stream() // потенциальные конфликты
							.filter(schi2 -> schi2 != schi1
									&& schi1.getWeekday() == schi2.getWeekday() // день_недели
									&& (schi2.getWeekplan() & schi1.getWeekplan()) > 0)// понедельный_план
							.filter(schi2 -> ((schi1.getTwain().getIdTwain() == schi2.getTwain()
									.getIdTwain())
									|| (schi1.getScheduleDiscipline()
											.getLessonType() == LessonType.lab4
											&& schi1.getTwain().getIdTwain() + 1 == schi2.getTwain()
													.getIdTwain())
									|| (schi2.getScheduleDiscipline()
											.getLessonType() == LessonType.lab4
											&& schi2.getTwain().getIdTwain() + 1 == schi1.getTwain()
													.getIdTwain())))
							.forEach(schi2 -> {
								System.err.println("ВОЗМОЖЕН КОНФЛИКТ: " + schi1.getIdScheduleItem()
										+ " и " + schi2.getIdScheduleItem());
								System.err.println(schi1.getScheduleDiscipline() + " "
										+ schi1.getClassrooms());
								System.err.println(schi2.getScheduleDiscipline() + " "
										+ schi2.getClassrooms());
								
								boolean classrooms = new ArrayList<Classroom>(schi2.getClassrooms())
										.removeIf(cl1 -> schi1
												.getClassrooms().stream().filter(cl2 -> cl1
														.getIdClassroom() == cl2.getIdClassroom())
												.findAny().isPresent());
								
								boolean lecturers = new ArrayList<Lecturer>(
										schi2.getScheduleDiscipline().getLecturers())
												.removeIf(
														cl1 -> schi1.getScheduleDiscipline()
																.getLecturers().stream()
																.filter(cl2 -> cl1.getUid() == cl2
																		.getUid())
																.findAny().isPresent());
								
								boolean discipline = schi1.getScheduleDiscipline().getDisc()
										.getIdDiscName() == schi2.getScheduleDiscipline().getDisc()
												.getIdDiscName();
								
								if ((classrooms || lecturers) && !discipline) {
									Conflict conflict = currentSession().get(Conflict.class,
											new ConflictId(schi1, schi2));
									if (conflict == null) conflict = new Conflict(schi1, schi2);
									conflict.setClassroomConflict(classrooms);
									conflict.setLecturerConflict(lecturers);
									currentSession().saveOrUpdate(conflict);
								}
							});
							
				}));
	}
	
	private void mergeFromRawSchedule(Schedule schedule, RawSchedule rawSchedule) {
		schedule.getScheduleDisciplines().stream().forEach(oldSD -> {
			ScheduleDiscipline newSD = rawSchedule
					.getScheduleDisciplines().stream().filter(newSD1 -> newSD1
							.getIdScheduleDiscipline() == oldSD.getIdScheduleDiscipline())
					.findAny().get();
			oldSD.setLecturers(newSD.getLecturers());
			List<ScheduleItem> listOldSchi = oldSD.getScheduleItems().stream()
					.filter(oldSchi -> !newSD
							.getScheduleItems().stream().filter(newSchi -> newSchi
									.getIdScheduleItem() == oldSchi.getIdScheduleItem())
							.findAny().isPresent())
					.peek(oldSchi -> {
						currentSession().delete(oldSchi);
					}).collect(Collectors.toList());
			oldSD.getScheduleItems().removeAll(listOldSchi);
			oldSD.getScheduleItems().forEach(schiOld -> {
				ScheduleItem schiNew = newSD
						.getScheduleItems().stream().filter(schiNew1 -> schiNew1
								.getIdScheduleItem() == schiOld.getIdScheduleItem())
						.findAny().get();
				schiOld.setClassrooms(schiNew.getClassrooms());
				schiOld.setComment(schiNew.getComment());
				schiOld.setTwain(schiNew.getTwain());
				schiOld.setWeekday(schiNew.getWeekday());
				schiOld.setWeekplan(schiNew.getWeekplan());
			});
			List<ScheduleItem> listNewSchi = newSD.getScheduleItems().stream()
					.filter(newSchi -> !oldSD.getScheduleItems().stream()
							.filter(oldSchi -> oldSchi.getIdScheduleItem() == newSchi
									.getIdScheduleItem())
							.findAny().isPresent())
					.collect(Collectors.toList());
			oldSD.getScheduleItems().addAll(listNewSchi);
		});
	}
}