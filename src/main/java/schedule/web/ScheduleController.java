package schedule.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

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
	
	@RequestMapping(path = "new-schedule", method = RequestMethod.POST)
	public String createSchedulePost(Schedule schedule, SessionStatus ss) {
		scheduleDAO.create(schedule);
		ss.setComplete();
		return "redirect:schedule-" + schedule.getIdSchedule() + "/edit";
	}
	
	@RequestMapping(path = "schedule-{idSchedule}/edit", method = RequestMethod.GET)
	public String editSchedule(@PathVariable("id") Integer idSemester,
			@PathVariable Integer idSchedule, Model model) {
		
		Schedule schedule = scheduleDAO.get(idSchedule);
		if (schedule == null) throw new ResourceNotFoundException();
		Semester semester = schedule.getEduProcGraphic().getSemester();
		Integer realIdSemester = semester.getIdSemester();
		if (realIdSemester != idSemester) return "redirect:../../semester-" + realIdSemester
				+ "/schedule-" + idSchedule + "/edit";
		
		model.addAttribute("schedule", schedule);
		model.addAttribute(semester);
		
		model.addAttribute("twains", twainDAO.getAll());
		
		PersonFinder personFinder = new PersonFinder();
		personFinder.setRole(Lecturer.class.getSimpleName().toLowerCase());
		model.addAttribute("lecturers", personDAO.getAll(personFinder));
		
		model.addAttribute("classrooms", classroomDAO.getAll());
		
		return "editSchedule";
	}
	
	@RequestMapping(path = "schedule-{idSchedule}/edit", method = RequestMethod.POST)
	public String editSchedulePost(@PathVariable Integer idSchedule, RawSchedule rawSchedule,
			Model model, SessionStatus ss) {
		
		System.err.println("=== START SAVING");
		
		scheduleDAO.update(idSchedule, rawSchedule);
		
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
	public List<Integer> getConflictingClassrooms(@RequestBody ScheduleItem scheduleItem) {
		return scheduleDAO.getConflictingClassrooms(scheduleItem);
	}
}
