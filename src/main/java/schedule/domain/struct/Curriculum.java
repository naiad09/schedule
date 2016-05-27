package schedule.domain.struct;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import schedule.domain.schedule.CurDiscipline;


/**
 * Класс учебного плана. Имеет профиль, год набора, тип обучения (очное,
 * заочное, очно-заочное, срок обучения, флаг составленного расписания. Имеет
 * ссылки на массив предметов, на график учебного процесса и список групп.
 */
@Entity
@Table(	name = "curriculum",
		uniqueConstraints = @UniqueConstraint(columnNames = { "id_profile",
				"id_enroll" }))
public class Curriculum {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_curriculum", unique = true, updatable = false)
	private Integer idCurriculum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profile", updatable = false)
	@NotNull
	private SkillProfile skillProfile;
	
	@Column(name = "schedule_done")
	@NotNull
	private boolean scheduleDone = false;
	
	@JoinColumn(name = "id_enroll")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Enrollment enrollment;
	
	@OneToMany(mappedBy = "curriculum", fetch = FetchType.LAZY)
	private List<CurDiscipline> curDisciplines = new ArrayList<CurDiscipline>(
			0);
	
	@OneToMany(mappedBy = "curriculum", fetch = FetchType.LAZY)
	private List<Group> groups = new ArrayList<Group>(0);
	
	// @Transient
	// public Integer getYearEnd() {
	// return LocalDate.of(getYearStart(), Month.AUGUST, 1)
	// .plus(getTrainingPeriod()).getYear();
	// }
	
	// @Transient
	// public Integer getCourse() {
	// LocalDate start = LocalDate.of(getYearStart(), Month.AUGUST, 1);
	// LocalDate now = LocalDate.now();
	// Period dif = Period.between(start, now);
	//
	// Period trainingPeriod = getTrainingPeriod();
	//
	// LocalDate end = start.plus(trainingPeriod);
	//
	// return now.isBefore(end) ? Integer.valueOf(dif.getYears() + 1) : null;
	// }
	
	public Integer getIdCurriculum() {
		return idCurriculum;
	}
	
	public void setIdCurriculum(Integer idCurriculum) {
		this.idCurriculum = idCurriculum;
	}
	
	public SkillProfile getSkillProfile() {
		return skillProfile;
	}
	
	public void setSkillProfile(SkillProfile skillProfile) {
		this.skillProfile = skillProfile;
	}
	
	public boolean isScheduleDone() {
		return scheduleDone;
	}
	
	public void setScheduleDone(boolean scheduleDone) {
		this.scheduleDone = scheduleDone;
	}
	
	public Enrollment getEnrollment() {
		return enrollment;
	}
	
	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}
	
	public List<CurDiscipline> getCurDisciplines() {
		return curDisciplines;
	}
	
	public void setCurDisciplines(List<CurDiscipline> curDisciplines) {
		this.curDisciplines = curDisciplines;
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	public enum EduMode {
		och, zao, oz
	}
}
