package schedule.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import schedule.dao.PersonDAO;
import schedule.domain.persons.EduDep;
import schedule.domain.persons.Lecturer;
import schedule.domain.persons.Person;
import schedule.domain.persons.Student;
import schedule.domain.struct.Chair;
import schedule.service.ResourceNotFoundException;


@Controller
@RequestMapping("persons")
@SessionAttributes(	types = { Chair.Faculty[].class, List.class },
					names = { "returnHere", "error" })
public class PersonController {
	
	@Autowired
	private PersonDAO personDAO;
	
	@RequestMapping("uid{personId}")
	public String getPerson(@PathVariable Integer personId, Model model) {
		
		Person find = personDAO.get(personId);
		if (find == null) throw new ResourceNotFoundException();
		model.addAttribute("person", find);
		
		return "person";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or "
			+ "(isAuthenticated() and principal.uid == #personId)")
	@RequestMapping(path = "uid{personId}/edit", method = RequestMethod.GET)
	public String editPerson(@PathVariable Integer personId, Model model) {
		getPerson(personId, model);
		return "common/editPerson";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or "
			+ "(isAuthenticated() and principal.uid == #personId)")
	@RequestMapping(path = "uid{personId}/edit", method = RequestMethod.POST)
	public String editPersonPost(@PathVariable Integer personId,
			@Valid @ModelAttribute("person") Person person,
			BindingResult result, Model model) {
		// TODO
		if (result.hasErrors()) {
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			model.addAttribute("error", true);
			model.addAttribute("person", person);
			return "common/editPerson";
		}
		
		personDAO.update(person);
		
		return "redirect: /uid" + personId + "?saved=true";
	}
	
	// @Secured("ROLE_ADMIN")
	@RequestMapping(path = "new-{person}", method = RequestMethod.GET)
	public String newPerson(@PathVariable Person person, Model model) {
		model.addAttribute("returnHere", true);
		if (person instanceof Student) {
		} else if (person instanceof Lecturer) {
		} else if (person instanceof EduDep) {
			model.addAttribute("faculties", Chair.Faculty.values());
		}
		
		return "common/newPerson";
	}
	
	// @Secured("ROLE_ADMIN")
	@RequestMapping(path = "new-{personType}", method = RequestMethod.POST)
	public String newPersonPost(@Valid @ModelAttribute("person") Person person,
			BindingResult result, Model model, boolean returnHere,
			SessionStatus ss) {
		System.out.println(person.getAuthData());
		
		if (result.hasErrors()) {
			model.addAttribute("error", true);
			return returnWithError(person);
		}
		try {
			personDAO.create(person);
		} catch (DataIntegrityViolationException e) {
			result.rejectValue("authData.login", "Unique.login");
			return returnWithError(person);
		}
		
		ss.setComplete();
		
		String returnString = returnHere ? "redirect: /nesw-" + person.getRole()
				: "redirect: /usid" + person.getUid();
		
		System.out.println(returnString);
		return returnString;
	}
	
	@RequestMapping("test")
	public String test() {
		return "redirect: /uid1";
	}
	
	private String returnWithError(Person person) {
		if (person.getAuthData() != null)
			person.getAuthData().setPassword(null);
		return "common/newPerson";
	}
}