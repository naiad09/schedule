package schedule.dao.util;

import schedule.domain.struct.Chair.Faculty;


public class ConflictFinder extends Finder {
	private Integer id;
	private boolean classroomConflict = true;
	private boolean lecturerConflict = true;
	private Faculty faculty;
	
	public ConflictFinder() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public boolean isClassroomConflict() {
		return classroomConflict;
	}
	
	public void setClassroomConflict(boolean classroomConflict) {
		this.classroomConflict = classroomConflict;
	}
	
	public boolean isLecturerConflict() {
		return lecturerConflict;
	}
	
	public void setLecturerConflict(boolean lecturerConflict) {
		this.lecturerConflict = lecturerConflict;
	}
	
	public Faculty getFaculty() {
		return faculty;
	}
	
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	
}
