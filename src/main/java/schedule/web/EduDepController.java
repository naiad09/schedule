package schedule.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("ed")
public class EduDepController {
	
	@RequestMapping("new-semester")
	public String newSemester() {
		return "";
	}
	
}
