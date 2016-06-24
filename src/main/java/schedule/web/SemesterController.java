package schedule.web;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import schedule.dao.ConflictDAO;
import schedule.dao.SemesterDAO;
import schedule.dao.util.ConflictFinder;
import schedule.domain.curriculum.CommonCurriculum;
import schedule.domain.schedule.Conflict;
import schedule.domain.semester.Semester;
import schedule.domain.struct.Enrollment;
import schedule.service.ResourceNotFoundException;


@Controller
@RequestMapping("ed")
@SessionAttributes({ "semester", "actualEnrolls" })
public class SemesterController {
	
	@Autowired
	private SemesterDAO semesterDAO;
	
	@Autowired
	private ConflictDAO conflictDAO;
	
	@RequestMapping("")
	public String getAllSemesters(Model model) {
		List<Semester> all = semesterDAO.getAll();
		model.addAttribute("semesters", all);
		return "ed";
	}
	
	@RequestMapping(path = "semester-{id}", method = RequestMethod.GET)
	public String showSemester(@PathVariable Integer id, Model model) {
		Semester semester = getSemester(id, model);
		List<Enrollment> actualEnrolls = semesterDAO.trainingInSemester(semester);
		model.addAttribute("actualEnrolls", actualEnrolls);
		
		System.out.println("from db: " + semester.getEduProcGraphics());
		semester.getEduProcGraphics().forEach(g -> {
			if (g.getCurriculums().size() > 0) {
				CommonCurriculum commonCurriculum = g.getCurriculums().get(0);
				if (commonCurriculum != null) g.setEnroll(commonCurriculum.getEnrollment());
			}
		});
		
		System.out.println("to view: " + semester.getEduProcGraphics());
		
		return "semester";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "new-semester", method = RequestMethod.GET)
	public String newSemester(Model model) {
		model.addAttribute("semester", Semester.calcNextSemester(Semester.calcCurrentSemester()));
		return "newSemester";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "new-semester", method = RequestMethod.POST)
	public String newSemesterPost(@Valid @ModelAttribute("semester") Semester semester,
			BindingResult result, SessionStatus ss) {
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(e -> System.out.println(e.toString()));
			return "newSemester";
		}
		try {
			semesterDAO.saveOrUpdate(semester);
		} catch (ConstraintViolationException exc) {
			result.rejectValue("semesterYear", "Unique");
			result.getAllErrors().forEach(e -> System.out.println(e.toString()));
			return "newSemester";
		}
		ss.setComplete();
		
		return "redirect:semester-" + semester.getIdSemester();
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "semester-{id}/edit", method = RequestMethod.GET)
	public String editSemester(@PathVariable Integer id, Model model) {
		showSemester(id, model);
		return "editSemester";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "semester-{id}/edit", method = RequestMethod.POST)
	public String editSemesterPost(@Valid @ModelAttribute("semester") Semester semester,
			BindingResult result, SessionStatus ss,
			@ModelAttribute("actualEnrolls") List<Enrollment> actualEnrolls) {
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(e -> System.out.println(e.toString()));
			return "editSemester";
		}
		
		semester.getEduProcGraphics().stream().forEach(g -> g.setSemester(semester));
		semesterDAO.saveOrUpdate(semester);
		
		System.out.println("SUCCESS!!!!!!");
		
		ss.setComplete();
		return "redirect:../semester-" + semester.getIdSemester();
	}
	
	@Secured("ROLE_EDUDEP")
	@RequestMapping(path = "semester-{id}/conflicts")
	public String getConflictManager(@PathVariable Integer id,
			@ModelAttribute ConflictFinder conflictFinder, Model model) {
		Semester semester = getSemester(id, model);
		List<Conflict> conflictList = conflictDAO.getBySemester(conflictFinder);
		model.addAttribute("conflictList", conflictList);
		model.addAttribute(semester);
		return "conflicts";
	}
	
	private Semester getSemester(Integer id, Model model) {
		Semester semester = semesterDAO.get(id);
		if (semester == null) throw new ResourceNotFoundException();
		model.addAttribute("semester", semester);
		return semester;
	}
	
}
