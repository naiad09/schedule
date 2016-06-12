package schedule.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import schedule.dao.ChairDAO;
import schedule.dao.GroupDAO;
import schedule.dao.PersonDAO;
import schedule.domain.persons.Lecturer;
import schedule.domain.persons.Person;
import schedule.domain.persons.Student;
import schedule.service.PersonFinder;
import schedule.service.ResourceNotFoundException;


/**
 * Контроллер, обеспечивающий основное взаимодействие с пользователями через
 * маппинг persons. Хранит в атрибутах Spring-сессии массив факультетов (для
 * добавления пользователей), а также другие списки и некоторые флаги.
 *
 */
@Controller
@RequestMapping("persons")
@SessionAttributes(names = { "faculties", "chairs", "groups" })
public class PersonController {
	
	@Autowired
	private PersonDAO personDAO;
	@Autowired
	private GroupDAO groupDAO;
	@Autowired
	private ChairDAO chairDAO;
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String getPersons(Model model,
			@ModelAttribute PersonFinder personFinder) {
		model.addAttribute("persons", personDAO.getAll(personFinder));
		return "common/persons";
	}
	
	/**
	 * Просмотр профиля пользователя. Добавляет объект пользователя в модель.
	 * Есль пользователь не найден, кидает исключение.
	 */
	@RequestMapping("uid-{personId}")
	public String getPerson(@PathVariable int personId, Model model)
			throws ResourceNotFoundException {
		
		Person find = personDAO.get(personId);
		if (find == null) throw new ResourceNotFoundException();
		model.addAttribute("person", find);
		
		return "person";
	}
	
	// TODO
	/**
	 * Редактирование профиля. Ограничено правами админа или хозяина профиля.
	 * Использует добавление в модель объекта пользователя из метода просмотра
	 * профиля.
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or "
			+ "(isAuthenticated() and principal.uid == #personId)")
	@RequestMapping(path = "uid-{personId}/edit", method = RequestMethod.GET)
	public String editPerson(@PathVariable int personId, Model model) {
		getPerson(personId, model);
		return "common/editPerson";
	}
	
	// TODO
	/** Пост редактирования профиля, ограничен аналогичными правами. */
	@PreAuthorize("hasRole('ROLE_ADMIN') or "
			+ "(isAuthenticated() and principal.uid == #personId)")
	@RequestMapping(path = "uid-{personId}/edit", method = RequestMethod.POST)
	public String editPersonPost(@PathVariable int personId,
			@Valid @ModelAttribute("person") Person person,
			BindingResult result, Model model) {
		// TODO
		if (result.hasErrors()) {
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			model.addAttribute("person", person);
			return "common/editPerson";
		}
		
		personDAO.update(person);
		
		return "redirect: /uid-" + personId + "?saved=true";
	}
	
	/**
	 * Новый пользователь, доступ сюда ограничен правами админа. Грузит и
	 * добавляет необходимые данные в зависимости от роли нового пользователя:
	 * для студента группы, для препода - кафедры, для учотдела - факультеты.
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "new-{person}", method = RequestMethod.GET)
	public String newPerson(@PathVariable Person person, Model model) {
		model.addAttribute("returnHere", true);
		if (person instanceof Student) {
			model.addAttribute("groups", groupDAO.getAll());
		} else if (person instanceof Lecturer) {
			model.addAttribute("chairs", chairDAO.getAll());
		}
		
		return "common/newPerson";
	}
	
	/**
	 * Пост нового профиля. Валидирует объект, если невалиден, возвращает на
	 * страницу редактирования с помеченными ошибками. Далее, после добавление
	 * записей в БД может вызвать эксепшены - они обрабатываются, и добавляют
	 * нужные ошибки в результат, возвращая на страницу редактирования. Далее в
	 * зависисомти от от флага returnHere редаректит на страницу редактирования
	 * или на новый созданный профиль.
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(path = "new-{personType}", method = RequestMethod.POST)
	public String newPersonPost(@Valid @ModelAttribute("person") Person person,
			BindingResult result, Model model, @RequestParam boolean returnHere,
			SessionStatus ss) {
		
		if (result.hasErrors()) {
			result.getAllErrors()
					.forEach(e -> System.out.println(e.toString()));
			return returnWithError(person, model, returnHere);
		}
		try {
			personDAO.saveOrUpdate(person);
		} catch (DataIntegrityViolationException e) {
			Matcher matcher = Pattern.compile("(login|email)")
					.matcher(e.getRootCause().toString());
			matcher.find();
			String group = matcher.group();
			result.rejectValue("authData." + group, "Unique." + group);
			return returnWithError(person, model, returnHere);
		}
		ss.setComplete();
		
		String returnString = returnHere ? "redirect:new-" + person.getRole()
				: "redirect:uid-" + person.getUid();
		if (returnHere) model.addAttribute("success", person.getUid());
		return returnString;
	}
	
	/**
	 * Возвращает на тсраницу редактирования с ошибкой. Обнуляет пароль для
	 * того, чтобы он не передавался обратно в нешифрованном виде.
	 */
	private String returnWithError(Person person, Model model,
			boolean returnHere) {
		if (person.getAuthData() != null)
			person.getAuthData().setPassword(null);
		model.addAttribute("error", true);
		model.addAttribute("returnHere", returnHere);
		return "common/newPerson";
	}
	
	/** Определяет, что можно забирать из формы строки == null */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class,
				new StringTrimmerEditor(true));
	}
}