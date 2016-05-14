package schedule.domain.struct;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Группа образовательных программ. Имеет код и название, а также ссылку на
 * программы.
 */
@Entity
@Table(name = "edu_prog_group")
public class EduProgGroup {
	
	private int eduProgGroupCode;
	private String eduProgGroupName;
	private Set<EduProgram> eduPrograms = new HashSet<EduProgram>(0);
	
	@Id
	@Column(name = "edu_prog_group_code", unique = true, updatable = false,
			nullable = false)
	public int getEduProgGroupCode() {
		return this.eduProgGroupCode;
	}
	
	public void setEduProgGroupCode(int eduProgGroupCode) {
		this.eduProgGroupCode = eduProgGroupCode;
	}
	
	@Column(name = "edu_prog_group_name", updatable = false, nullable = false)
	public String getEduProgGroupName() {
		return this.eduProgGroupName;
	}
	
	public void setEduProgGroupName(String eduProgGroupName) {
		this.eduProgGroupName = eduProgGroupName;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "eduProgGroup")
	public Set<EduProgram> getEduPrograms() {
		return this.eduPrograms;
	}
	
	public void setEduPrograms(Set<EduProgram> eduPrograms) {
		this.eduPrograms = eduPrograms;
	}
	
}
