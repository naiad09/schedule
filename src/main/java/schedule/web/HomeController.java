package schedule.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import schedule.dao.ChairDAO;
import schedule.dao.PersonDAO;
import schedule.dao.SemesterDAO;
import schedule.domain.struct.Chair;
import schedule.domain.struct.Chair.Faculty;
import schedule.service.CustomUserDetails;


@Controller
public class HomeController {
	
	@Autowired
	private ChairDAO chairDAO;
	@Autowired
	private PersonDAO personDAO;
	@Autowired
	private SemesterDAO semesterDAO;
	
	@RequestMapping("")
	public String home(Authentication auth, Model model, HttpSession ses) {
		
		System.out.println(semesterDAO.getCurrent());
		
		// TODO
		if (auth != null) {
			CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
			ses.setAttribute("currentUser", personDAO.get(cud.getUid()));
		}
		
		List<Chair> all = chairDAO.getAll();
		Map<Faculty, List<Chair>> collect = all.stream()
				.collect(Collectors.groupingBy(c -> c.getFaculty()));
		model.addAttribute("chairsMap", collect);
		
		return "common/home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Principal principal) {
		if (principal == null) return "common/login";
		else return "redirect:/";
	}
	
	@RequestMapping(value = "/error{errorCode}")
	public String error(@PathVariable int errorCode) {
		return "common/error" + errorCode;
	}
	
}