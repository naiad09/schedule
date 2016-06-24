package schedule.dao.util;

import schedule.domain.persons.Lecturer;
import schedule.domain.persons.LecturerJob;
import schedule.domain.persons.Person;
import schedule.domain.struct.Chair;


/**
 * Критерии для поиска пользователей: часть имени, роль, и специфические
 * характеристики для ролей: для студента номер зачетки, для преподавателя
 * ученая степень и(или) должность, для учебного отдела факультет. Также можно
 * задать поиск по логину: нет логина, есть логин, конкретный логин. Также
 * задает параметры отображения: число пользователей на странице и номер
 * страницы (не поставляются в запрос, используются на jsp для отображения).
 */
public class PersonFinder extends Finder {
	private String role;
	private String name;
	private Person.Gender gender;
	
	private Chair.Faculty faculty;
	private Lecturer.Degree degree;
	private LecturerJob.JobType jobType;
	private Integer recordBookNumber;
	
	private String login;
	private Boolean loginExists;
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Chair.Faculty getFaculty() {
		return faculty;
	}
	
	public void setFaculty(Chair.Faculty faculty) {
		this.faculty = faculty;
	}
	
	public Lecturer.Degree getDegree() {
		return degree;
	}
	
	public void setDegree(Lecturer.Degree degree) {
		this.degree = degree;
	}
	
	public LecturerJob.JobType getJobType() {
		return jobType;
	}
	
	public void setJobType(LecturerJob.JobType jobType) {
		this.jobType = jobType;
	}
	
	public Integer getRecordBookNumber() {
		return recordBookNumber;
	}
	
	public void setRecordBookNumber(Integer recordBookNumber) {
		this.recordBookNumber = recordBookNumber;
	}
	
	public Person.Gender getGender() {
		return gender;
	}
	
	public void setGender(Person.Gender gender) {
		this.gender = gender;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public Boolean getLoginExists() {
		return loginExists;
	}
	
	public void setLoginExists(Boolean loginExists) {
		this.loginExists = loginExists;
	}
	
}
