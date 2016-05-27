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
import javax.validation.constraints.Size;


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
	@Column(name = "id_profile", updatable = false, unique = true)
	private int idProfile;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_edu_prog", updatable = false)
	@NotNull
	private EduProgram eduProgram;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_chair", updatable = false)
	@NotNull
	private Chair chair;
	
	@Column(name = "profile_name", updatable = false, unique = true)
	@Size(max = 150, min = 10)
	private String profileName;
	
	@OneToMany(mappedBy = "skillProfile", fetch = FetchType.LAZY)
	private List<Curriculum> curriculums = new ArrayList<Curriculum>(0);
	
	public int getIdProfile() {
		return idProfile;
	}
	
	public void setIdProfile(int idProfile) {
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
	
	public List<Curriculum> getCurriculums() {
		return curriculums;
	}
	
	public void setCurriculums(List<Curriculum> curriculums) {
		this.curriculums = curriculums;
	}
	
}
