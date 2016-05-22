package schedule.web;

import schedule.domain.persons.Lecturer;
import schedule.domain.struct.Chair;
import schedule.domain.struct.LecturerJob;


/**
 * Критерии для поиска пользователей: часть имени, роль, и специфические
 * характеристики для ролей: для студента номер зачетки, для преподавателя
 * ученая степень и(или) должность, для учебного отдела факультет. Также задает
 * параметры отображения: число пользователей на странице и номер страницы (не
 * поставляются в запрос, используются на jsp для отображения).
 */
public class PersonFinder {
	private String role;
	private String name;
	
	private int perPage = 20;
	private int page = 1;
	
	private Chair.Faculty faculty;
	private Lecturer.Degree degree;
	private LecturerJob.JobType jobType;
	private Integer recordBookNumber;
	
	// TODO с логином: есть, нет, без разницы, конкретный логин
	
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
	
	public int getPerPage() {
		return perPage;
	}
	
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
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
	
}