package schedule.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import schedule.dao.SimpleGenericDAO;
import schedule.domain.struct.Chair;


@Controller
public class FacultyController {
	
	@Autowired
	private SimpleGenericDAO<Chair> chairDAO;
	
	@RequestMapping({ "vf", "frt", "fe", "faitu", "fvt", "ief", "hi", "vi" })
	public String faculty(Model model, HttpServletRequest req,
			@PathVariable String string) {
		
		System.out.println(req.getRequestURI());
		System.out.println(string);
		
		return "common/login";
	}
}