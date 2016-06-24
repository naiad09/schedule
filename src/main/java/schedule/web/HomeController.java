package schedule.web;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import schedule.dao.PersonDAO;
import schedule.dao.SemesterDAO;
import schedule.service.security.CustomUserDetails;


/**
 * Домашний контроллер, управляет главной страницей, страницей входа
 * пользователей и страницей ошибки 404.
 */
@Controller
public class HomeController {
	@Autowired
	private PersonDAO personDAO;
	@Autowired
	private SemesterDAO semesterDAO;
	
	@RequestMapping("")
	public String home(Authentication auth, Model model, HttpSession ses) {
		
		model.addAttribute("currentSemester", semesterDAO.getCurrent());
		
		if (auth != null) {
			CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
			ses.setAttribute("currentUser", personDAO.get(cud.getUid()));
		}
		
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Principal principal) {
		if (principal == null) return "login";
		else return "redirect:/";
	}
	
	@RequestMapping(value = "/error404")
	public String error404() {
		return "error404";
	}
	
	@RequestMapping(value = "/error403")
	public String error403() {
		return "error403";
	}
	
}