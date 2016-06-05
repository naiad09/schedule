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
import schedule.domain.struct.Chair;
import schedule.domain.struct.Chair.Faculty;
import schedule.service.ResourceNotFoundException;


@Controller
@RequestMapping({ "vf", "frt", "fe", "faitu", "fvt", "ief", "hi", "vi" })
public class UniverStructController {
	
	@Autowired
	private ChairDAO chairDAO;
	
	@RequestMapping("")
	public String getFaculty(HttpServletRequest req, Model model) {
		Faculty f = getFacultyFromRequest(req);
		
		List<Chair> chairs = chairDAO.findAllByFaculty(f);
		model.addAttribute("faculty", f);
		model.addAttribute("chairs", chairs);
		
		return "common/faculty";
	}
	
	@RequestMapping("{chairShort}")
	public String getChair(HttpServletRequest req,
			@PathVariable String chairShort, Model model) {
		Chair findChair = chairDAO.findFull(chairShort);
		if (findChair == null
				|| findChair.getFaculty() != getFacultyFromRequest(req))
			throw new ResourceNotFoundException();
		model.addAttribute("chair", findChair);
		return "chair";
	}
	
	private Faculty getFacultyFromRequest(HttpServletRequest req) {
		Matcher matcher = Pattern.compile("^/([a-z]+)/?")
				.matcher(req.getServletPath());
		matcher.find();
		String group = matcher.group(1);
		Faculty f = Faculty.valueOf(group);
		if (f == null) throw new ResourceNotFoundException();
		return f;
	}
}
