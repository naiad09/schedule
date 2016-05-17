package schedule.web;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import schedule.dao.GenericDAO;
import schedule.dao.PersonDao;
import schedule.domain.persons.Person;
import schedule.domain.struct.Chair;
import schedule.service.CustomUserDetails;


@Controller
public class PersonController {
	
	@Autowired
	private GenericDAO<Chair, Integer> chairDAO;
	@Autowired
	private PersonDao personDAO;
	
	@RequestMapping("/")
	public String home(Authentication auth, Model model, HttpSession ses) {
		// TODO
		if (auth != null) {
			CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
			ses.setAttribute("currentUser", personDAO.read(cud.getUid()));
		}
		
		List<Chair> all = chairDAO.getAll();
		model.addAttribute("chairs", all);
		
		return "common/home";
	}
	
	@RequestMapping("/persons/uid{personId}")
	public String getPerson(@PathVariable Integer personId, Model model) {
		
		Person find = personDAO.read(personId);
		if (find == null) throw new ResourceNotFoundException();
		model.addAttribute("person", find);
		
		return "person";
	}
	
	// @PreAuthorize("hasRole('ROLE_ADMIN') or "
	// + "(isAuthenticated() and principal.uid == #personId)")
	@RequestMapping(path = "/persons/uid{personId}/edit",
					method = RequestMethod.GET)
	public String editPerson(@PathVariable Integer personId, Model model) {
		getPerson(personId, model);
		return "common/editPerson";
	}
	
	// @PreAuthorize("hasRole('ROLE_ADMIN') or "
	// + "(isAuthenticated() and principal.uid == #personId)")
	@RequestMapping(path = "/persons/uid{personId}/edit",
					method = RequestMethod.POST)
	// TODO
	public String editPersonPost(@PathVariable Integer personId,
			@Valid @ModelAttribute("person") Person person,
			BindingResult result) {
		
		if (result.hasErrors()) return "common/editPerson?error=true";
		
		personDAO.update(person);
		
		return "redirect: /persons/uid" + personId + "?saved=true";
	}
}