package schedule.service;

import org.springframework.core.convert.converter.Converter;

import schedule.domain.persons.EduDep;
import schedule.domain.persons.Lecturer;
import schedule.domain.persons.Person;
import schedule.domain.persons.Student;


public class PersonConverter implements Converter<String, Person> {
	
	public Person convert(String personType) {
		Person person = null;
		switch (personType) {
			case "Student":
				person = new Student();
				break;
			case "Lecturer":
				person = new Lecturer();
				break;
			case "EduDep":
				person = new EduDep();
				break;
			default:
				throw new IllegalArgumentException(
						"Unknown person type:" + personType);
		}
		return person;
	}
	
}
