package schedule.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import schedule.dao.MinimalGenericDAO;
import schedule.dao.ScheduleDAO;
import schedule.domain.schedule.Schedule;
import schedule.domain.schedule.Twain;
import schedule.service.ResourceNotFoundException;


@Controller
@RequestMapping("ed")
@SessionAttributes({ "schedule", "twains" })
public class ScheduleController {
	
	@Autowired
	private ScheduleDAO scheduleDAO;
	
	@Autowired
	private MinimalGenericDAO<Twain> twaindDAO;
	
	// @Secured("ROLE_ADMIN")
	@RequestMapping(path = "new-schedule", method = RequestMethod.POST)
	public String createSchedulePost(Schedule schedule) {
		scheduleDAO.create(schedule);
		return "redirect:schedule-" + schedule.getIdSchedule() + "/edit";
	}
	
	// @Secured("ROLE_ADMIN")
	@RequestMapping(path = "schedule-{idSchedule}/edit",
					method = RequestMethod.GET)
	public String editSchedule(@PathVariable Integer idSchedule, Model model) {
		Schedule schedule = scheduleDAO.get(idSchedule);
		if (schedule == null) throw new ResourceNotFoundException();
		model.addAttribute("schedule", schedule);
		model.addAttribute("twains", twaindDAO.getAll());
		return "common/editSchedule";
	}
}
