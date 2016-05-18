package schedule.domain.struct;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import schedule.domain.schedule.Classroom;
import schedule.domain.schedule.CurDiscipline;


/**
 * Кафедра. Привязана к факультету, имеет название и сокращенное название. К
 * кафедре привязаны аудитории, профили и дисциплины, а также преподаватели.
 */
@Entity
@Table(name = "chair")
public class Chair {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_chair", updatable = false, unique = true)
	private Integer idChair;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "id_faculty", updatable = false,
			columnDefinition = "enum(vf,frt,fe,faitu,fvt,ief,hi,vi)")
	@NotNull
	private Faculty faculty;
	
	@NotNull
	@Column(name = "chair_fullname", updatable = false, unique = true)
	@Size(max = 100, min = 5)
	private String fullName;
	
	@NotNull
	@Column(name = "chair_shortname", updatable = false, unique = true)
	@Size(max = 32, min = 2)
	private String shortName;
	
	@NotNull
	@Column(name = "chair_shortname_eng", updatable = false, unique = true)
	@Size(max = 8, min = 2)
	private String shortNameEng;
	
	@OneToMany(mappedBy = "chair", fetch = FetchType.LAZY)
	@OrderBy("jobType") // , lecturer.degree, lecturer.lastName,
						// lecturer.firstName, lecturer.middleName")
	// TODO order by not works...
	@ElementCollection(targetClass = LecturerJob.class)
	private Set<LecturerJob> lecturerJobs = new HashSet<LecturerJob>(0);
	
	@OneToMany(mappedBy = "chair", fetch = FetchType.LAZY)
	private Set<Classroom> classrooms = new HashSet<Classroom>(0);
	
	@OneToMany(mappedBy = "chair", fetch = FetchType.LAZY)
	private Set<SkillProfile> skillProfiles = new HashSet<SkillProfile>(0);
	
	@OneToMany(mappedBy = "chair", fetch = FetchType.LAZY)
	private Set<CurDiscipline> curDisciplines = new HashSet<CurDiscipline>(0);
	
	public Integer getIdChair() {
		return idChair;
	}
	
	public void setIdChair(Integer idChair) {
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
	
	public Set<LecturerJob> getLecturerJobs() {
		return lecturerJobs;
	}
	
	public void setLecturerJobs(Set<LecturerJob> lecturerJobs) {
		this.lecturerJobs = lecturerJobs;
	}
	
	public Set<Classroom> getClassrooms() {
		return classrooms;
	}
	
	public void setClassrooms(Set<Classroom> classrooms) {
		this.classrooms = classrooms;
	}
	
	public Set<SkillProfile> getSkillProfiles() {
		return skillProfiles;
	}
	
	public void setSkillProfiles(Set<SkillProfile> skillProfiles) {
		this.skillProfiles = skillProfiles;
	}
	
	public Set<CurDiscipline> getCurDisciplines() {
		return curDisciplines;
	}
	
	public void setCurDisciplines(Set<CurDiscipline> curDisciplines) {
		this.curDisciplines = curDisciplines;
	}
	
	public String getChairShortNameEng() {
		return shortNameEng;
	}
	
	public void setChairShortNameEng(String shortNameEng) {
		this.shortNameEng = shortNameEng;
	}
	
	public enum Faculty {
		vf, frt, fe, faitu, fvt, ief, hi, vi;
	}
	
}
