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

import schedule.domain.schedule.Classroom;
import schedule.domain.schedule.CurDiscipline;


/**
 * Кафедра. Привязана к факультету, имеет название и сокращенное название. К
 * кафедре привязаны аудитории, профили и дисциплины, а также преподаватели.
 */
@Entity
@Table(name = "chair")
public class Chair {
	
	private Integer idChair;
	private Faculty faculty;
	private String chairName;
	private String chairShortname;
	private Set<LecturerJob> lecturerJobs = new HashSet<LecturerJob>(0);
	private Set<Classroom> classrooms = new HashSet<Classroom>(0);
	private Set<SkillProfile> skillProfiles = new HashSet<SkillProfile>(0);
	private Set<CurDiscipline> curDisciplines = new HashSet<CurDiscipline>(0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_chair", updatable = false, unique = true,
			nullable = false)
	public Integer getIdChair() {
		return this.idChair;
	}
	
	public void setIdChair(Integer idChair) {
		this.idChair = idChair;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "id_faculty", updatable = false,
			columnDefinition = "enum(vf,frt,fe,faitu,fvt,ief,hi,vi)",
			nullable = false, length = 5)
	public Faculty getFaculty() {
		return this.faculty;
	}
	
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	
	@Column(name = "chair_name", updatable = false, nullable = false,
			length = 100)
	public String getChairName() {
		return this.chairName;
	}
	
	public void setChairName(String chairName) {
		this.chairName = chairName;
	}
	
	@Column(name = "chair_shortname", updatable = false, nullable = false,
			length = 32)
	public String getChairShortname() {
		return this.chairShortname;
	}
	
	public void setChairShortname(String chairShortname) {
		this.chairShortname = chairShortname;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chair")
	public Set<LecturerJob> getLecturerJobs() {
		return this.lecturerJobs;
	}
	
	public void setLecturerJobs(Set<LecturerJob> lecturerJobs) {
		this.lecturerJobs = lecturerJobs;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chair")
	public Set<Classroom> getClassrooms() {
		return this.classrooms;
	}
	
	public void setClassrooms(Set<Classroom> classrooms) {
		this.classrooms = classrooms;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chair")
	public Set<SkillProfile> getSkillProfiles() {
		return this.skillProfiles;
	}
	
	public void setSkillProfiles(Set<SkillProfile> skillProfiles) {
		this.skillProfiles = skillProfiles;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chair")
	public Set<CurDiscipline> getCurDisciplines() {
		return this.curDisciplines;
	}
	
	public void setCurDisciplines(Set<CurDiscipline> curDisciplines) {
		this.curDisciplines = curDisciplines;
	}
	
	public enum Faculty {
		vf, frt, fe, faitu, fvt, ief, hi, vi;
	}
	
}
