package schedule.web;

import java.util.List;

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
@RequestMapping("ed")
@Secured("ROLE_EDUDEP")
@SessionAttributes({ "schedule", "twains", "lecturers", "classrooms" })
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
	public String createSchedulePost(Schedule schedule) {
		scheduleDAO.create(schedule);
		return "redirect:schedule-" + schedule.getIdSchedule() + "/edit";
	}
	
	@RequestMapping(path = "schedule-{idSchedule}/edit", method = RequestMethod.GET)
	public String editSchedule(@PathVariable Integer idSchedule, Model model) {
		
		Schedule schedule = scheduleDAO.get(idSchedule);
		if (schedule == null) throw new ResourceNotFoundException();
		model.addAttribute("schedule", schedule);
		
		model.addAttribute("twains", twainDAO.getAll());
		
		PersonFinder personFinder = new PersonFinder();
		personFinder.setRole(Lecturer.class.getSimpleName().toLowerCase());
		model.addAttribute("lecturers", personDAO.getAll(personFinder));
		
		model.addAttribute("classrooms", classroomDAO.getAll());
		
		return "editSchedule";
	}
	
	@RequestMapping(path = "schedule-{idSchedule}/edit", method = RequestMethod.POST)
	public String editSchedulePost(RawSchedule rawSchedule, Model model, SessionStatus ss) {
		
		Schedule schedule = (Schedule) model.asMap().get("schedule");
		
		System.err.println("=== START SAVING");
		
		scheduleDAO.update(schedule, rawSchedule);
		
		System.err.println("== Я СОХРАНИЛСЯ!!!! ==");
		
		scheduleDAO.searchConflicts(schedule);
		ss.setComplete();
		
		System.err.println("=== END SAVING");
		return "redirect:edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "schedule-{idSchedule}/edit/conflict-classrooms",
					method = RequestMethod.GET)
	public List<Integer> getConflictingClassrooms(@RequestBody ScheduleItem scheduleItem) {
		return scheduleDAO.getConflictingClassrooms(scheduleItem);
	}
}
