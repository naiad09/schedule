package schedule.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import schedule.dao.ConflictDAO;
import schedule.dao.MinimalGenericDAO;
import schedule.dao.PersonDAO;
import schedule.dao.ScheduleDAO;
import schedule.dao.util.PersonFinder;
import schedule.dao.util.RawSchedule;
import schedule.domain.persons.Lecturer;
import schedule.domain.schedule.Classroom;
import schedule.domain.schedule.Schedule;
import schedule.domain.schedule.ScheduleItem;
import schedule.domain.schedule.Twain;
import schedule.domain.semester.Semester;
import schedule.service.ResourceNotFoundException;


/**
 * Контроллер редактирования расписания, доступен только для работников учебного
 * отдела. Разграничивает доступ к расписаниям в соответствии с факультетом, к
 * которому прикреплен Работник учебного отдела. Связан с DAO расписания, DAO
 * персон, DAO учебных пар, DAO аудиторий. Хранит в атрибутах сессии список
 * преподавателей, список кафедр, редактируемое расписание и список аудиторий.
 * Позволяет создать новое расписание, редактировать расписание, а также
 * получить список конфликтных аудиторий для редактируемого элемента расписания.
 */

@Controller
@RequestMapping("ed/semester-{id}")
@Secured("ROLE_EDUDEP")
@SessionAttributes({ "twains", "lecturers", "classrooms" })
public class ScheduleController {
	
	@Autowired
	private ScheduleDAO scheduleDAO;
	@Autowired
	private MinimalGenericDAO<Twain> twainDAO;
	@Autowired
	private MinimalGenericDAO<Classroom> classroomDAO;
	@Autowired
	private PersonDAO personDAO;
	@Autowired
	private ConflictDAO conflictDAO;
	
	@RequestMapping("schedule-{idSchedule}")
	@Secured({})
	public String getSchedule(@PathVariable("id") Integer idSemester,
			@PathVariable Integer idSchedule, Model model) {
		try {
			findScheduleForModel(idSemester, idSchedule, model);
			return "schedule";
		} catch (IncorrectSemesterScheduleException e) {
			return "redirect:../../semester-" + e.realSemester + "/schedule-" + idSchedule;
		}
	}
	
	@RequestMapping(path = "new-schedule", method = RequestMethod.POST)
	public String createSchedulePost(Schedule schedule, SessionStatus ss) {
		scheduleDAO.create(schedule);
		ss.setComplete();
		return "redirect:schedule-" + schedule.getIdSchedule() + "/edit";
	}
	
	@RequestMapping(path = "schedule-{idSchedule}/edit", method = RequestMethod.GET)
	public String editSchedule(@PathVariable("id") Integer idSemester,
			@PathVariable Integer idSchedule, Model model) {
		try {
			findScheduleForModel(idSemester, idSchedule, model);
			return "editSchedule";
		} catch (IncorrectSemesterScheduleException e) {
			return "redirect:../../semester-" + e.realSemester + "/schedule-" + idSchedule
					+ "/edit";
		}
	}
	
	@RequestMapping(path = "schedule-{idSchedule}/edit", method = RequestMethod.POST)
	public String editSchedulePost(@PathVariable Integer idSchedule, RawSchedule rawSchedule,
			Model model, SessionStatus ss) {
		
		System.err.println("=== START SAVING");
		
		Schedule schedule = scheduleDAO.update(idSchedule, rawSchedule);
		scheduleDAO.searchConflicts(schedule);
		
		System.err.println("== Я СОХРАНИЛСЯ!!!! ==");
		ss.setComplete();
		
		System.err.println("=== END SAVING");
		return "redirect:edit";
	}
	
	@RequestMapping(path = "schedule-{idSchedule}/delete", method = RequestMethod.POST)
	public String deleteSchedule(@PathVariable Integer idSchedule, HttpServletRequest request) {
		Schedule schedule = scheduleDAO.get(idSchedule);
		if (schedule == null) throw new ResourceNotFoundException();
		scheduleDAO.delete(schedule);
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
	
	@ResponseBody
	@RequestMapping(value = "schedule-{idSchedule}/edit/conflict-classrooms",
					method = RequestMethod.GET)
	public List<Integer> getConflictingClassrooms(ScheduleItem scheduleItem,
			@PathVariable("id") Integer idSemester) {
		System.out.println(scheduleItem);
		List<Integer> conflictingClassrooms = conflictDAO.getConflictingClassrooms(scheduleItem,
				idSemester);
		System.out.println(conflictingClassrooms);
		return conflictingClassrooms;
	}
	
	private void findScheduleForModel(Integer idSemester, Integer idSchedule, Model model)
			throws IncorrectSemesterScheduleException {
		Schedule schedule = scheduleDAO.get(idSchedule);
		if (schedule == null) throw new ResourceNotFoundException();
		Semester semester = schedule.getEduProcGraphic().getSemester();
		Integer realIdSemester = semester.getIdSemester();
		if (realIdSemester != idSemester)
			throw new IncorrectSemesterScheduleException(realIdSemester);
		
		addToModel(model, semester, schedule);
	}
	
	@Secured({})
	void addToModel(Model model, Semester semester, Schedule schedule) {
		if (schedule == null) return;
		model.addAttribute(schedule);
		model.addAttribute(semester);
		
		model.addAttribute("twains", twainDAO.getAll());
		
		PersonFinder personFinder = new PersonFinder();
		personFinder.setRole(Lecturer.class.getSimpleName().toLowerCase());
		model.addAttribute("lecturers", personDAO.findPersons(personFinder));
		
		model.addAttribute("classrooms", classroomDAO.getAll());
	}
	
	public class IncorrectSemesterScheduleException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		private Integer realSemester;
		
		public IncorrectSemesterScheduleException(Integer realSemester) {
			this.realSemester = realSemester;
		}
		
	}
}
