package schedule.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import schedule.dao.PersonDao;
import schedule.dao.SimpleGenericDAO;
import schedule.domain.persons.Person;
import schedule.domain.struct.Chair;


@Controller
public class HomeController {
	
	@Autowired
	private SimpleGenericDAO<Chair> chairDAO;
	@Autowired
	private PersonDao personDao;
	
	@RequestMapping("/")
	public String home(Model model) {
		
		List<Chair> all = chairDAO.getAll();
		model.addAttribute("chairs", all);
		
		return "common/home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) return "common/login";
		else return "redirect:/";
	}
	
	@RequestMapping(value = "/error404")
	public String error404() {
		return "common/error404";
	}
	
	@RequestMapping(value = "/error403")
	public String error403() {
		return "common/error403";
	}
	
	@RequestMapping("/persons/uid{personId}")
	public String person(@PathVariable Integer personId, Model model) {
		
		Person find = personDao.find(personId);
		if (find == null) return error404();
		model.addAttribute("person", find);
		
		return "person";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or principal.userId == #id")
	@RequestMapping(path = "/persons/uid{personId}/edit",
					method = RequestMethod.GET)
	public String personEdit(@PathVariable Integer personId, Model model) {
		
		Person find = personDao.find(personId);
		if (find == null) return error404();
		model.addAttribute("person", find);
		
		return "common/personEdit";
	}
}