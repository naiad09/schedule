package schedule.domain.struct;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Образовательная программа. Имеет код, название и квалификацию выпускника.
 * Привязана к группе образовательных программ, также имеет ссылку на профили.
 */
@Entity
@Table(name = "edu_program")
public class EduProgram {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_edu_prog", unique = true, updatable = false)
	private int idEduProg;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "edu_prog_group_code", updatable = false)
	@NotNull
	private EduProgGroup eduProgGroup;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "id_qual_type", updatable = false,
			columnDefinition = "enum('bac','mag','spec','asp')")
	private EduQual eduQual = EduQual.bac;
	
	@NotNull
	@Column(name = "edu_prog_code", updatable = false)
	@Size(max = 8, min = 7)
	private String eduProgCode;
	
	@Column(name = "edu_prog_name", updatable = false)
	@NotNull
	private String eduProgName;
	
	@OneToMany(mappedBy = "eduProgram",fetch=FetchType.LAZY)
	private Set<SkillProfile> skillProfiles = new HashSet<SkillProfile>(0);
	
	public int getIdEduProg() {
		return idEduProg;
	}
	
	public void setIdEduProg(int idEduProg) {
		this.idEduProg = idEduProg;
	}
	
	public EduProgGroup getEduProgGroup() {
		return eduProgGroup;
	}
	
	public void setEduProgGroup(EduProgGroup eduProgGroup) {
		this.eduProgGroup = eduProgGroup;
	}
	
	public EduQual getEduQual() {
		return eduQual;
	}
	
	public void setEduQual(EduQual eduQual) {
		this.eduQual = eduQual;
	}
	
	public String getEduProgCode() {
		return eduProgCode;
	}
	
	public void setEduProgCode(String eduProgCode) {
		this.eduProgCode = eduProgCode;
	}
	
	public String getEduProgName() {
		return eduProgName;
	}
	
	public void setEduProgName(String eduProgName) {
		this.eduProgName = eduProgName;
	}
	
	public Set<SkillProfile> getSkillProfiles() {
		return skillProfiles;
	}
	
	public void setSkillProfiles(Set<SkillProfile> skillProfiles) {
		this.skillProfiles = skillProfiles;
	}
	
	public enum EduQual {
		bac, mag, spec, asp;
	}
	
}
