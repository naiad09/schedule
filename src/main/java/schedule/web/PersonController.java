package schedule.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import schedule.dao.ChairDAO;
import schedule.dao.PersonDAO;
import schedule.dao.SemesterDAO;
import schedule.domain.persons.Person;
import schedule.domain.struct.Chair;
import schedule.domain.struct.Chair.Faculty;
import schedule.service.CustomUserDetails;
import schedule.service.ResourceNotFoundException;


@Controller
public class PersonController {
	
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
	
	@RequestMapping("/persons/uid{personId}")
	public String getPerson(@PathVariable Integer personId, Model model) {
		
		Person find = personDAO.get(personId);
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
		return "common/editPerson";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or "
			+ "(isAuthenticated() and principal.uid == #personId)")
	@RequestMapping(path = "/persons/uid{personId}/edit",
					method = RequestMethod.POST)
	public String editPersonPost(@PathVariable Integer personId,
			@Valid @ModelAttribute("person") Person person,
			BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			model.addAttribute("error", true);
			model.addAttribute("person", person);
			return "common/editPerson";
		}
		
		personDAO.update(person);
		
		return "redirect: /persons/uid" + personId + "?saved=true";
	}
}