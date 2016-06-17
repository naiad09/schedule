package schedule.domain.curriculum;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import schedule.domain.persons.Group;
import schedule.domain.struct.SkillProfile;


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
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_curriculum", unique = true, updatable = false)
	private Integer idCurriculum;
	
	@ManyToOne
	@JoinColumn(name = "id_profile", updatable = false, nullable = false)
	private SkillProfile skillProfile;
	
	@ManyToOne
	@JoinColumn(name = "id_common_curriculum", updatable = false,
				nullable = false)
	private CommonCurriculum commonCurriculum;
	
	@OneToMany(mappedBy = "curriculum")
	private List<Group> groups = new ArrayList<Group>(0);
	
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
	
	public List<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	@Override
	public String toString() {
		return "Curriculum [idCurriculum=" + idCurriculum + "]";
	}
	
}
