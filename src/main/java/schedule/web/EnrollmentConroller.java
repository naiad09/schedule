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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import schedule.dao.EduProgGroupDAO;
import schedule.dao.EnrollmentDAO;
import schedule.domain.struct.Enrollment;


@Controller
@RequestMapping("enroll")
@SessionAttributes("eduProgGroups")
public class EnrollmentConroller {
	
	@Autowired
	private EnrollmentDAO enrollmentDAO;
	
	@Autowired
	private EduProgGroupDAO eduProgGroupDAO;
	
	// @Secured("ROLE_ADMIN")
	@RequestMapping(path = "new", method = RequestMethod.GET)
	public String newEnroll(Model model) {
		model.addAttribute("enroll", new Enrollment());
		model.addAttribute("eduProgGroups", eduProgGroupDAO.getAll());
		return "common/enroll";
	}
	
	// @Secured("ROLE_ADMIN")
	@RequestMapping(path = "new", method = RequestMethod.POST)
	public String enrollPost(@Valid @ModelAttribute("enroll") Enrollment enroll,
			BindingResult result, SessionStatus ss) {
		
		if (result.hasErrors()) {
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			return "common/enroll";
		}
		
		try {
			enrollmentDAO.saveOrUpdate(enroll);
		} catch (ConstraintViolationException ex) {
			result.rejectValue("", "Unique");
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			return "common/enroll";
		}
		ss.setComplete();
		return "redirect:" + enroll.getYearStart() + "/" + enroll.getEduQual();
	}
}
