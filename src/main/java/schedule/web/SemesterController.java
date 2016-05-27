package schedule.web;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import schedule.dao.SemesterDAO;
import schedule.domain.schedule.Semester;


@Controller
@RequestMapping("ed")
@Deprecated
public class SemesterController {
	
	@Autowired
	private SemesterDAO semesterDAO;
	
	@RequestMapping(path = "new-semester", method = RequestMethod.GET)
	public String newSemester(Model model) {
		model.addAttribute("semester",
				Semester.getNextSemester(Semester.getCurrentSemester()));
		return "common/semester";
	}
	
	@RequestMapping(path = "new-semester", method = RequestMethod.POST)
	public String newSemesterPost(
			@Valid @ModelAttribute("semester") Semester semester,
			BindingResult result, SessionStatus ss) {
		
		if (result.hasErrors()) {
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			return "common/semester";
		}
		try {
			semesterDAO.saveOrUpdate(semester);
		} catch (ConstraintViolationException exc) {
			result.rejectValue("semesterYear", "Unique");
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			return "common/semester";
		}
		ss.setComplete();
		
		return "redirect:semester-" + semester.getIdSemester();
	}
}
