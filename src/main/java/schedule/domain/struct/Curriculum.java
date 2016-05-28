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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import schedule.domain.persons.Group;
import schedule.domain.schedule.ProfileDiscipline;


/**
 * Класс учебного плана. Имеет профиль, год набора, тип обучения (очное,
 * заочное, очно-заочное, срок обучения, флаг составленного расписания. Имеет
 * ссылки на массив предметов, на график учебного процесса и список групп.
 */
@Entity
@Table(	name = "curriculum",
		uniqueConstraints = @UniqueConstraint(columnNames = { "id_profile",
				"id_common_curriculum" }))
public class Curriculum {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_curriculum", unique = true, updatable = false)
	private Integer idCurriculum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profile", updatable = false)
	@NotNull
	private SkillProfile skillProfile;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_common_curriculum", updatable = false)
	@NotNull
	private CommonCurriculum commonCurriculum = new CommonCurriculum();
	
	@OneToMany(mappedBy = "curriculum", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private List<ProfileDiscipline> profileDisciplines = new ArrayList<ProfileDiscipline>(
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
	
	public CommonCurriculum getCommonCurriculum() {
		return commonCurriculum;
	}
	
	public void setCommonCurriculum(CommonCurriculum commonCurriculum) {
		this.commonCurriculum = commonCurriculum;
	}
	
	public List<ProfileDiscipline> getProfileDisciplines() {
		return profileDisciplines;
	}
	
	public void setProfileDisciplines(
			List<ProfileDiscipline> profileDisciplines) {
		this.profileDisciplines = profileDisciplines;
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
}
