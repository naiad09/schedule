package schedule.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import schedule.dao.PersonDAO;
import schedule.domain.persons.EduDep;
import schedule.domain.persons.Lecturer;
import schedule.domain.persons.Person;
import schedule.domain.persons.Student;
import schedule.domain.struct.Chair;
import schedule.service.ResourceNotFoundException;


@Controller
@RequestMapping("persons")
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
			BindingResult result, Model model) {
		newPerson(person, model);
		if (result.hasErrors()) {
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			model.addAttribute("error", true);
			return "common/newPerson";
		}
		personDAO.create(person);
		model.addAttribute("success", true);
		
		return "redirect: common/newPerson";
	}
}