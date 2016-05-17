package schedule.domain.struct;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import schedule.domain.converters.TrainingPeriodAttributeConverter;
import schedule.domain.schedule.CurDiscipline;


/**
 * Класс учебного плана. Имеет профиль, год набора, тип обучения (очное,
 * заочное, очно-заочное), срок обучения, флаг составленного расписания. Имеет
 * ссылки на массив предметов, на график учебного процесса и список групп.
 */
@Entity
@Table(name = "curriculum")
public class Curriculum {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_curriculum", unique = true, updatable = false)
	@NotNull
	private Integer idCurriculum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profile", updatable = false)
	@NotNull
	private SkillProfile skillProfile;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "edu_mode", updatable = false,
			columnDefinition = "enum(och,zao,oz)")
	@NotNull
	private EduMode eduMode;
	
	@Column(name = "year_start", updatable = false)
	@NotNull
	private int yearStart;
	
	@Convert(converter = TrainingPeriodAttributeConverter.class)
	@Column(name = "training_period", updatable = false)
	@NotNull
	private Period trainingPeriod;
	
	@Column(name = "schedule_done")
	@NotNull
	private boolean scheduleDone = false;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "curriculum")
	private Set<CurDiscipline> curDisciplines = new HashSet<CurDiscipline>(0);
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "curriculum_semester",
				joinColumns = { @JoinColumn(name = "id_curriculum",
											updatable = false) },
				inverseJoinColumns = { @JoinColumn(	name = "id_edu_period",
													updatable = false) })
	private Set<EduProcGraphic> eduProcGraphics = new HashSet<EduProcGraphic>(
			0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "curriculum")
	private Set<Group> groups = new HashSet<Group>(0);
	
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
	
	public EduMode getEduMode() {
		return eduMode;
	}
	
	public void setEduMode(EduMode eduMode) {
		this.eduMode = eduMode;
	}
	
	public int getYearStart() {
		return yearStart;
	}
	
	public void setYearStart(int yearStart) {
		this.yearStart = yearStart;
	}
	
	public Period getTrainingPeriod() {
		return trainingPeriod;
	}
	
	public void setTrainingPeriod(Period trainingPeriod) {
		this.trainingPeriod = trainingPeriod;
	}
	
	public boolean isScheduleDone() {
		return scheduleDone;
	}
	
	public void setScheduleDone(boolean scheduleDone) {
		this.scheduleDone = scheduleDone;
	}
	
	public Set<CurDiscipline> getCurDisciplines() {
		return curDisciplines;
	}
	
	public void setCurDisciplines(Set<CurDiscipline> curDisciplines) {
		this.curDisciplines = curDisciplines;
	}
	
	public Set<EduProcGraphic> getEduProcGraphics() {
		return eduProcGraphics;
	}
	
	public void setEduProcGraphics(Set<EduProcGraphic> eduProcGraphics) {
		this.eduProcGraphics = eduProcGraphics;
	}
	
	public Set<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
	
	@Transient
	public Integer getYearEnd() {
		return LocalDate.of(getYearStart(), Month.AUGUST, 1)
				.plus(getTrainingPeriod()).getYear();
	}
	
	@Transient
	public Integer getCourse() {
		LocalDate start = LocalDate.of(getYearStart(), Month.AUGUST, 1);
		LocalDate now = LocalDate.now();
		Period dif = Period.between(start, now);
		
		Period trainingPeriod = getTrainingPeriod();
		
		LocalDate end = start.plus(trainingPeriod);
		
		return now.isBefore(end) ? Integer.valueOf(dif.getYears() + 1) : null;
	}
	
	public enum EduMode {
		och, zao, oz
	}
}
