package schedule.service.converters;

import org.springframework.core.convert.converter.Converter;

import schedule.domain.persons.EduDep;
import schedule.domain.persons.Lecturer;
import schedule.domain.persons.Person;
import schedule.domain.persons.Student;


public class PersonConverter implements Converter<String, Person> {
	
	public Person convert(String personType) {
		if (personType == null) return null;
		Person person = null;
		switch (personType) {
			case "student":
				person = new Student();
				break;
			case "lecturer":
				person = new Lecturer();
				break;
			case "edudep":
				person = new EduDep();
				break;
			default:
				throw new IllegalArgumentException("Unknown person type:" + personType);
		}
		return person;
	}
	
}
