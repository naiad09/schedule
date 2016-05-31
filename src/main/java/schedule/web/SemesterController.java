package schedule.web;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import schedule.dao.EnrollmentDAO;
import schedule.dao.SemesterDAO;
import schedule.domain.schedule.Semester;
import schedule.domain.struct.Enrollment;
import schedule.service.ResourceNotFoundException;


@Controller
@RequestMapping("ed")
@SessionAttributes({ "semester", "actualEnrolls" })
public class SemesterController {
	
	@Autowired
	private SemesterDAO semesterDAO;
	
	@Autowired
	private EnrollmentDAO enrollmentDAO;
	
	// @Secured("ROLE_ADMIN")
	@RequestMapping(path = "new-semester", method = RequestMethod.GET)
	public String newSemester(Model model) {
		model.addAttribute("semester",
				Semester.getNextSemester(Semester.getCurrentSemester()));
		return "common/newSemester";
	}
	
	// @Secured("ROLE_ADMIN")
	@RequestMapping(path = "new-semester", method = RequestMethod.POST)
	public String newSemesterPost(
			@Valid @ModelAttribute("semester") Semester semester,
			BindingResult result, SessionStatus ss) {
		
		if (result.hasErrors()) {
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			return "common/newSemester";
		}
		try {
			semesterDAO.saveOrUpdate(semester);
		} catch (ConstraintViolationException exc) {
			result.rejectValue("semesterYear", "Unique");
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			return "common/newSemester";
		}
		ss.setComplete();
		
		return "redirect:semester-" + semester.getIdSemester();
	}
	
	@RequestMapping("")
	public String allSemesters(Model model) {
		List<Semester> all = semesterDAO.getAll();
		model.addAttribute("semesters", all);
		return "common/ed";
	}
	
	@Deprecated
	@RequestMapping("semester-{id}")
	public String getSemester(@PathVariable Integer id, Model model) {
		Semester find = semesterDAO.get(id);
		if (find == null) throw new ResourceNotFoundException();
		model.addAttribute("semester", find);
		return "semester";
	}
	
	@RequestMapping(path = "semester-{id}/edit", method = RequestMethod.GET)
	public String editSemester(@PathVariable Integer id, Model model) {
		Semester find = semesterDAO.get(id);
		if (find == null) throw new ResourceNotFoundException();
		model.addAttribute("semester", find);
		List<Enrollment> actualEnrolls = enrollmentDAO.trainingInSemester(find);
		// model.addAttribute("actualEnrolls", actualEnrolls);
		
		return "common/editSemester";
	}
	
	@RequestMapping(path = "semester-{id}/edit", method = RequestMethod.POST)
	public String editSemesterPost(
			@Valid @ModelAttribute("semester") Semester semester,
			BindingResult result, SessionStatus ss) {
		
		if (result.hasErrors()) {
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			return "common/editSemester";
		}
		
		System.out.println("SUCCESS!!!!!!");
		return "common/editSemester";
		// return "redirect:semester-" + s.getIdSemester();
	}
}
