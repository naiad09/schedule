package schedule.web;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import schedule.dao.ChairDAO;
import schedule.dao.MinimalGenericDAO;
import schedule.dao.SemesterDAO;
import schedule.domain.persons.Group;
import schedule.domain.semester.Semester;
import schedule.domain.struct.Chair;
import schedule.domain.struct.Chair.Faculty;
import schedule.service.ResourceNotFoundException;


@Controller
@RequestMapping({ "vf", "frt", "fe", "faitu", "fvt", "ief", "hi", "vi" })
public class UniverStructController {
	
	@Autowired
	private ChairDAO chairDAO;
	@Autowired
	private MinimalGenericDAO<Group> groupDAO;
	@Autowired
	private SemesterDAO semesterDAO;
	
	@Autowired
	private ScheduleController scheduleController;

	@RequestMapping("")
	public String getFaculty(HttpServletRequest req, Model model) {
		Faculty f = getFacultyFromRequest(req);
		
		List<Chair> chairs = chairDAO.findAllByFaculty(f);
		model.addAttribute("faculty", f);
		model.addAttribute("chairs", chairs);
		
		return "faculty";
	}
	
	@RequestMapping("{chairShort}")
	public String getChair(HttpServletRequest req, @PathVariable String chairShort, Model model) {
		Chair findChair = chairDAO.findFull(chairShort);
		if (findChair == null) throw new ResourceNotFoundException();
		Faculty facultyFromRequest = getFacultyFromRequest(req);
		Faculty faculty = findChair.getFaculty();
		if (faculty != facultyFromRequest) return "redirect:" + faculty + "/" + chairShort;
		model.addAttribute("chair", findChair);
		return "chair";
	}
	
	@RequestMapping("group-{idGroup}")
	public String getGroup(HttpServletRequest req, @PathVariable Integer idGroup, Model model) {
		Group group = groupDAO.get(idGroup);
		if (group == null) throw new ResourceNotFoundException();
		Faculty facultyFromRequest = getFacultyFromRequest(req);
		Faculty faculty = group.getCurriculum().getSkillProfile().getChair().getFaculty();
		if (faculty != facultyFromRequest) return "redirect:/" + faculty + "/group-" + idGroup;
		model.addAttribute(group);
		
		Semester currentSemester = semesterDAO.getCurrent();
		scheduleController.addToModel(model, currentSemester,
				group.getSchedules().stream()
						.filter(schedule -> schedule.getEduProcGraphic().getSemester()
								.getIdSemester() == currentSemester.getIdSemester())
						.findAny().orElse(null));
		return "group";
	}
	
	private Faculty getFacultyFromRequest(HttpServletRequest req) {
		Matcher matcher = Pattern.compile("^/([a-z]+)/?").matcher(req.getServletPath());
		matcher.find();
		String group = matcher.group(1);
		Faculty f = Faculty.valueOf(group);
		if (f == null) throw new ResourceNotFoundException();
		return f;
	}
}
