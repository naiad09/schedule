package schedule.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import schedule.dao.CurriculumDAO;
import schedule.domain.curriculum.CommonCurriculum;
import schedule.service.ResourceNotFoundException;


@Controller
@RequestMapping("cur")
public class CurriculumController {
	
	@Autowired
	private CurriculumDAO curriculumDAO;
	
	@RequestMapping("id-{idCur}")
	public String getCurriculum(@PathVariable Integer idCur, Model model) {
		CommonCurriculum cur = curriculumDAO.get(idCur);
		if (cur == null) throw new ResourceNotFoundException();
		model.addAttribute("cur", cur);
		return "curriculum";
	}
	
}
