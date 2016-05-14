package schedule.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import schedule.domain.Chair;
import schedule.domain.persons.HttpAuth;
import schedule.domain.persons.Person;


@Controller
public class HomeController {
	
	@Autowired
	private SimpleGenericDAO<Chair> chairDAO;
	@Autowired
	private PersonDao personDao;
	
	@RequestMapping({ "/", "index" })
	public String home(Model model, HttpServletRequest req) {
		
		List<Chair> all = chairDAO.getAll();
		model.addAttribute("chairs", all);
		
		Authentication userPrincipal = (Authentication) req.getUserPrincipal();
		if (userPrincipal != null) req.getSession().setAttribute("currentUser",
				((HttpAuth) userPrincipal.getPrincipal()).getPerson());
		else req.getSession().setAttribute("currentUser", null);
		
		return "common/home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		
		if (auth instanceof AnonymousAuthenticationToken) return "common/login";
		else return "redirect:/index";
	}
	
	@RequestMapping(value = "/error404")
	public String error404() {
		return "common/error404";
	}
	
	@RequestMapping("/persons/uid{personId}")
	public String person(@PathVariable Integer personId,
			Map<String, Object> map) {
		
		Person find = personDao.find(personId);
		if (find == null) return error404();
		map.put("person", find);
		
		return "person";
	}
}