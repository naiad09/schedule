package schedule.service;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;


public class CustomLocalDateEditor extends PropertyEditorSupport {
	
	public void setAsText(String text) {
		if ("".equals(text)) setValue(null);
		else {
			LocalDate parse = LocalDate.parse(text);
			setValue(parse);
		}
	}
	
}
