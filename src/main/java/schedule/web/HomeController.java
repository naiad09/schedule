package schedule.web;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import schedule.dao.PersonDao;
import schedule.dao.SimpleGenericDAO;
import schedule.domain.persons.Person;
import schedule.domain.struct.Chair;
import schedule.service.CustomUserDetails;


@Controller
public class HomeController {
	
	@Autowired
	private SimpleGenericDAO<Chair> chairDAO;
	@Autowired
	private PersonDao personDao;
	
	@RequestMapping("/")
	public String home(Authentication auth, Model model, HttpSession ses) {
		
		if (auth != null) {
			CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
			ses.setAttribute("currentUser", personDao.find(cud.getUid()));
		}
		
		List<Chair> all = chairDAO.getAll();
		model.addAttribute("chairs", all);
		
		return "common/home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Principal principal) {
		if (principal == null) return "common/login";
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
	public String getPerson(@PathVariable Integer personId, Model model) {
		
		Person find = personDao.find(personId);
		if (find == null) throw new ResourceNotFoundException();
		model.addAttribute("person", find);
		
		return "person";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or "
			+ "(isAuthenticated() and principal.uid == #personId)")
	@RequestMapping(path = "/persons/uid{personId}/edit",
					method = RequestMethod.GET)
	public String editPerson(@PathVariable Integer personId, Model model) {
		getPerson(personId, model);
		return "common/personEdit";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("profile")
	public String getProfile(Authentication auth, Model model) {
		CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
		return getPerson(cud.getUid(), model);
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("profile/edit")
	public String editProfile(Authentication auth, Model model) {
		CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
		return editPerson(cud.getUid(), model);
	}
}