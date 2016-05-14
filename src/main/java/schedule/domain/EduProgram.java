package schedule.domain;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Образовательная программа. Имеет код, название и квалификацию выпускника.
 * Привязана к группе образовательных программ, также имеет ссылку на профили.
 */
@Entity
@Table(name = "edu_program")
public class EduProgram {
	
	private int idEduProg;
	private EduProgGroup eduProgGroup;
	private EduQual eduQual = EduQual.bac;
	private String eduProgCode;
	private String eduProgName;
	private Set<SkillProfile> skillProfiles = new HashSet<SkillProfile>(0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_edu_prog", unique = true, updatable = false,
			nullable = false)
	public Integer getIdEduProg() {
		return this.idEduProg;
	}
	
	public void setIdEduProg(Integer idEduProg) {
		this.idEduProg = idEduProg;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "edu_prog_group_code", updatable = false,
				nullable = false)
	public EduProgGroup getEduProgGroup() {
		return this.eduProgGroup;
	}
	
	public void setEduProgGroup(EduProgGroup eduProgGroup) {
		this.eduProgGroup = eduProgGroup;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "id_qual_type", updatable = false, nullable = false,
			columnDefinition = "enum('bac','mag','spec','asp')")
	public EduQual getEduQual() {
		return this.eduQual;
	}
	
	public void setEduQual(EduQual eduQual) {
		this.eduQual = eduQual;
	}
	
	@Column(name = "edu_prog_code", updatable = false, nullable = false,
			length = 8)
	public String getEduProgCode() {
		return this.eduProgCode;
	}
	
	public void setEduProgCode(String eduProgCode) {
		this.eduProgCode = eduProgCode;
	}
	
	@Column(name = "edu_prog_name", updatable = false, nullable = false)
	public String getEduProgName() {
		return this.eduProgName;
	}
	
	public void setEduProgName(String eduProgName) {
		this.eduProgName = eduProgName;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eduProgram")
	public Set<SkillProfile> getSkillProfiles() {
		return this.skillProfiles;
	}
	
	public void setSkillProfiles(Set<SkillProfile> skillProfiles) {
		this.skillProfiles = skillProfiles;
	}
	
	public enum EduQual {
		bac, mag, spec, asp;
	}
	
}
