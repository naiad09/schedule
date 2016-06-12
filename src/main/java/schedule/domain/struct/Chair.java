package schedule.domain.struct;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import schedule.domain.curriculum.ProfileDiscipline;
import schedule.domain.persons.LecturerJob;
import schedule.domain.schedule.Classroom;


/**
 * Кафедра. Привязана к факультету, имеет название и сокращенное название. К
 * кафедре привязаны аудитории, профили и дисциплины, а также преподаватели.
 */
@Entity
@Table(name = "chair")
public class Chair {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_chair", updatable = false, unique = true)
	private int idChair;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "id_faculty", updatable = false,
			columnDefinition = "enum(vf,frt,fe,faitu,fvt,ief,hi,vi)")
	@NotNull
	private Faculty faculty;
	
	@Column(name = "chair_fullname", updatable = false, unique = true)
	@Size(max = 100, min = 5)
	@NotEmpty
	private String fullName;
	
	@Column(name = "chair_shortname", updatable = false, unique = true)
	@Size(max = 32, min = 2)
	private String shortName;
	
	@Column(name = "chair_shortname_eng", updatable = false, unique = true)
	@Size(max = 8, min = 2)
	@NotEmpty
	private String shortNameEng;
	
	@OneToMany(mappedBy = "id.chair")
	@OrderBy("jobType") // , lecturer.degree, lecturer.lastName,
						// lecturer.firstName, lecturer.middleName")
	// TODO order by not works...
	@ElementCollection(targetClass = LecturerJob.class)
	private List<LecturerJob> lecturerJobs = new ArrayList<LecturerJob>(0);
	
	@OneToMany(mappedBy = "chair")
	private List<Classroom> classrooms = new ArrayList<Classroom>(0);
	
	@OneToMany(mappedBy = "chair")
	private List<SkillProfile> skillProfiles = new ArrayList<SkillProfile>(0);
	
	@OneToMany(mappedBy = "chair")
	private List<ProfileDiscipline> curDisciplines = new ArrayList<ProfileDiscipline>(
			0);
	
	public int getIdChair() {
		return idChair;
	}
	
	public void setIdChair(int idChair) {
		this.idChair = idChair;
	}
	
	public Faculty getFaculty() {
		return faculty;
	}
	
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getShortNameEng() {
		return shortNameEng;
	}
	
	public void setShortNameEng(String shortNameEng) {
		this.shortNameEng = shortNameEng;
	}
	
	public List<LecturerJob> getLecturerJobs() {
		return lecturerJobs;
	}
	
	public void setLecturerJobs(List<LecturerJob> lecturerJobs) {
		this.lecturerJobs = lecturerJobs;
	}
	
	public List<Classroom> getClassrooms() {
		return classrooms;
	}
	
	public void setClassrooms(List<Classroom> classrooms) {
		this.classrooms = classrooms;
	}
	
	public List<SkillProfile> getSkillProfiles() {
		return skillProfiles;
	}
	
	public void setSkillProfiles(List<SkillProfile> skillProfiles) {
		this.skillProfiles = skillProfiles;
	}
	
	public List<ProfileDiscipline> getCurDisciplines() {
		return curDisciplines;
	}
	
	public void setCurDisciplines(List<ProfileDiscipline> curDisciplines) {
		this.curDisciplines = curDisciplines;
	}
	
	public String getChairShortNameEng() {
		return shortNameEng;
	}
	
	public void setChairShortNameEng(String shortNameEng) {
		this.shortNameEng = shortNameEng;
	}
	
	public enum Faculty {
		frt, fe, faitu, fvt, ief, hi, vi, vf;
	}
	
}
