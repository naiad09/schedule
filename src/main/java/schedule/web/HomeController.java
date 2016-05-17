package schedule.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {
	
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
	
}