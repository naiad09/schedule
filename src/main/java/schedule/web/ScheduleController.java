package schedule.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import schedule.dao.GenericDAO;
import schedule.dao.ScheduleDAO;
import schedule.domain.persons.Group;
import schedule.domain.schedule.Schedule;


@Controller
@SessionAttributes({ "groups", "schedule" })
public class ScheduleController {
	
	@Autowired
	private ScheduleDAO scheduleDAO;
	
	@Autowired
	private GenericDAO<Group> groupDAO;
	
	// @Secured("ROLE_ADMIN")
	@RequestMapping(path = "schedule/new", method = RequestMethod.GET)
	public String createSchedule(Model model) {
		model.addAttribute("schedule", new Schedule());
		model.addAttribute("groups", groupDAO.getAll());
		return "common/newSchedule";
	}
	
	// @Secured("ROLE_ADMIN")
	@RequestMapping(path = "schedule/new", method = RequestMethod.POST)
	public String createSchedulePost(
			@Valid @ModelAttribute("schedule") Schedule schedule,
			BindingResult result, Model model,
			@ModelAttribute("groups") List<Group> groups) {
		
		Integer idGroup = schedule.getGroup().getIdGroup();
		if (idGroup == null) result.rejectValue("group.idGroup", "NotNull");
		
		if (result.hasErrors()) {
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			return "common/newSchedule";
		}
		
		if (scheduleDAO.isExists(schedule)) {
			result.rejectValue("", "Unique");
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			return "common/newSchedule";
		}
		
		Group group = groups.stream().filter(g -> g.getIdGroup() == idGroup)
				.findFirst().get();
		schedule.setGroup(group);
		model.addAttribute("schedule", schedule);
		
		return "forward:edit";
	}
	
	@RequestMapping(path = "schedule/edit", method = RequestMethod.POST)
	public String editSchedule() {
		
		return "";
	}
}
