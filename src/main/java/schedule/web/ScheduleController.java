package schedule.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import schedule.domain.persons.Lecturer;
import schedule.domain.schedule.Classroom;
import schedule.domain.schedule.Schedule;
import schedule.domain.schedule.ScheduleItem;
import schedule.domain.schedule.Twain;
import schedule.service.PersonFinder;
import schedule.service.ResourceNotFoundException;


@Controller
@RequestMapping("ed")
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
	
	// @Secured("ROLE_EDUDEP")
	@RequestMapping(path = "new-schedule", method = RequestMethod.POST)
	public String createSchedulePost(Schedule schedule) {
		scheduleDAO.create(schedule);
		return "redirect:schedule-" + schedule.getIdSchedule() + "/edit";
	}
	
	// @Secured("ROLE_EDUDEP")
	@RequestMapping(path = "schedule-{idSchedule}/edit",
					method = RequestMethod.GET)
	public String editSchedule(@PathVariable Integer idSchedule, Model model) {
		Schedule schedule = scheduleDAO.get(idSchedule);
		if (schedule == null) throw new ResourceNotFoundException();
		model.addAttribute("schedule", schedule);
		model.addAttribute("twains", twainDAO.getAll());
		PersonFinder personFinder = new PersonFinder();
		personFinder.setRole(Lecturer.class.getSimpleName().toLowerCase());
		model.addAttribute("lecturers", personDAO.getAll(personFinder));
		model.addAttribute("classrooms", classroomDAO.getAll());
		return "common/editSchedule";
	}
	
	// @Secured("ROLE_EDUDEP")
	@RequestMapping(path = "schedule-{idSchedule}/edit",
					method = RequestMethod.POST)
	public String editSchedulePost(
			@Valid @ModelAttribute("schedule") Schedule schedule,
			BindingResult result, Model model, SessionStatus ss) {
		
		System.out.println("HERE " + result.hasErrors());
		
		return "common/editSchedule";
	}
	
	@ResponseBody
	@RequestMapping(value = "schedule-{idSchedule}/edit/conflict-classrooms",
					method = RequestMethod.GET)
	public List<String> getConflictingClassrooms(
			@RequestBody ScheduleItem scheduleItem) {
		List<String> records = new ArrayList<String>();
		records.add("Record #1");
		records.add("Record #2");
		
		return records;
		// return scheduleDAO.getConflictingClassrooms(scheduleItem);
	}
}
