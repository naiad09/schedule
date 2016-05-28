package schedule.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import schedule.dao.CurriculumDAO;
import schedule.domain.struct.Curriculum;
import schedule.service.ResourceNotFoundException;


@Controller
@RequestMapping("cur")
public class CurriculumController {
	
	@Autowired
	private CurriculumDAO curriculumDAO;
	
	@RequestMapping("id-{idCur}")
	public String getCurriculum(@PathVariable Integer idCur, Model model) {
		List<Curriculum> curs = curriculumDAO.getCurriculumsById(idCur);
		if (curs.isEmpty()) throw new ResourceNotFoundException();
		curs.get(0).getCurDisciplines().size();
		model.addAttribute("curs", curs);
		return "eduProg";
	}
	
}
