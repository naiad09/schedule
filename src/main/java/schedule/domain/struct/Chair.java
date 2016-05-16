package schedule.domain.struct;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	@Column(name = "chair_name", updatable = false)
	@Size(max = 100, min = 5)
	private String chairName;
	
	@NotNull
	@Column(name = "chair_shortname", updatable = false)
	@Size(max = 32, min = 2)
	private String chairShortname;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chair")
	private Set<LecturerJob> lecturerJobs = new HashSet<LecturerJob>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chair")
	private Set<Classroom> classrooms = new HashSet<Classroom>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chair")
	private Set<SkillProfile> skillProfiles = new HashSet<SkillProfile>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chair")
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
	
	public String getChairName() {
		return chairName;
	}
	
	public void setChairName(String chairName) {
		this.chairName = chairName;
	}
	
	public String getChairShortname() {
		return chairShortname;
	}
	
	public void setChairShortname(String chairShortname) {
		this.chairShortname = chairShortname;
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
	
	public enum Faculty {
		vf, frt, fe, faitu, fvt, ief, hi, vi;
	}
	
}
