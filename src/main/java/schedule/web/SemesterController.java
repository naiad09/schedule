package schedule.web;

import java.util.ArrayList;
import java.util.Comparator;
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
import schedule.domain.struct.CommonCurriculum;
import schedule.domain.struct.EduProcGraphic;
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
		Semester semester = semesterDAO.get(id);
		if (semester == null) throw new ResourceNotFoundException();
		model.addAttribute("semester", semester);
		List<Enrollment> actualEnrolls = enrollmentDAO
				.trainingInSemester(semester);
		model.addAttribute("actualEnrolls", actualEnrolls);
		
		// prepareSemesterToView(find, actualEnrolls);
		
		System.out.println("from db: " + semester.getEduProcGraphics());
		semester.getEduProcGraphics().forEach(g -> {
			if (g.getCurriculums().size() > 0) {
				CommonCurriculum commonCurriculum = g.getCurriculums().get(0);
				if (commonCurriculum != null)
					g.setEnroll(commonCurriculum.getEnrollment());
			}
		});
		
		System.out.println("to view: " + semester.getEduProcGraphics());
		
		return "common/editSemester";
	}
	
	private void prepareSemesterToView(Semester semester,
			List<Enrollment> actualEnrolls) {
		System.out.println(
				"graphics getted count:" + semester.getEduProcGraphics());
		semester.getEduProcGraphics().forEach(g -> {
			if (g.getCurriculums().size() > 0) {
				CommonCurriculum commonCurriculum = g.getCurriculums().get(0);
				if (commonCurriculum != null)
					g.setEnroll(commonCurriculum.getEnrollment());
			}
		});
		
		actualEnrolls.forEach(a -> {
			EduProcGraphic eduProcGraphic = semester.getEduProcGraphics()
					.stream().filter(g -> g.getEnroll() == a)
					.sorted(new Comparator<EduProcGraphic>() {
						public int compare(EduProcGraphic o1,
								EduProcGraphic o2) {
							return o1.getCurriculums().size()
									- o2.getCurriculums().size();
						};
					}).findFirst().orElse(null);
			if (eduProcGraphic == null) {
				EduProcGraphic e = new EduProcGraphic();
				e.setEnroll(a);
				semester.getEduProcGraphics().add(e);
				System.out.println("add new:" + e + a);
			} else {
				System.out.println("set default:" + eduProcGraphic + a);
			}
		});
	}
	
	@RequestMapping(path = "semester-{id}/edit", method = RequestMethod.POST)
	public String editSemesterPost(
			@Valid @ModelAttribute("semester") Semester semester,
			BindingResult result, SessionStatus ss,
			@ModelAttribute("actualEnrolls") List<Enrollment> actualEnrolls) {
		
		if (result.hasErrors()) {
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			return "common/editSemester";
		}
		
		// prepareSemesterToSave(semester, actualEnrolls);
		semester.getEduProcGraphics().stream()
				.forEach(g -> g.setSemester(semester));
		semesterDAO.saveOrUpdate(semester);
		
		System.out.println("SUCCESS!!!!!!");
		
		ss.setComplete();
		// return "common/editSemester";
		return "redirect:edit";
	}
	
	private void prepareSemesterToSave(Semester semester,
			List<Enrollment> actualEnrolls) {
		System.out.println("prepare start:" + semester.getEduProcGraphics());
		List<CommonCurriculum> suchDefined = new ArrayList<>();
		
		semester.getEduProcGraphics().stream()
				.filter(g -> !g.getCurriculums().isEmpty())
				.forEach(g -> suchDefined.addAll(g.getCurriculums()));
		
		System.out.println("such defined: " + suchDefined.size());
		
		semester.getEduProcGraphics().stream()
				.peek(g -> g.setSemester(semester))
				.filter(g -> g.getCurriculums().isEmpty()).forEach(g -> {
					Enrollment enrollment = actualEnrolls
							.stream().filter(a -> a.getIdEnroll() == g
									.getEnroll().getIdEnroll())
							.findFirst().get();
					g.setCurriculums(enrollment.getCommonCurriculums());
					final List<CommonCurriculum> toRemove = new ArrayList<>();
					g.getCurriculums().forEach(c -> {
						suchDefined.forEach(s -> {
							if (c.getIdCommonCurriculum() == s
									.getIdCommonCurriculum())
								toRemove.add(c);
						});
					});
					g.getCurriculums().removeAll(toRemove);
					System.out.println("enroll " + enrollment.getIdEnroll()
							+ ", " + enrollment.getCommonCurriculums().size());
				});
		
		System.out.println("prepare end:" + semester.getEduProcGraphics());
		
	}
}
