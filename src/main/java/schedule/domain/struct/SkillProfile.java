package schedule.domain.struct;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

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


/**
 * Образовательный профиль. Расширяет образовательную программу. Привязан к
 * кафедре. Также имеет название.
 */
@Entity
@Table(	name = "skill_profile",
		uniqueConstraints = @UniqueConstraint(columnNames = "profile_name"))
public class SkillProfile {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_profile", updatable = false, unique = true,
			nullable = false)
	private Integer idProfile;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_edu_prog", updatable = false, nullable = false)
	private EduProgram eduProgram;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_chair", updatable = false, nullable = false)
	private Chair chair;
	
	@Column(name = "profile_name", updatable = false, unique = true,
			length = 150)
	private String profileName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "skillProfile")
	private Set<Curriculum> curriculums = new HashSet<Curriculum>(0);
	
	public Integer getIdProfile() {
		return idProfile;
	}
	
	public void setIdProfile(Integer idProfile) {
		this.idProfile = idProfile;
	}
	
	public EduProgram getEduProgram() {
		return eduProgram;
	}
	
	public void setEduProgram(EduProgram eduProgram) {
		this.eduProgram = eduProgram;
	}
	
	public Chair getChair() {
		return chair;
	}
	
	public void setChair(Chair chair) {
		this.chair = chair;
	}
	
	public String getProfileName() {
		return profileName;
	}
	
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	public Set<Curriculum> getCurriculums() {
		return curriculums;
	}
	
	public void setCurriculums(Set<Curriculum> curriculums) {
		this.curriculums = curriculums;
	}
	
}
