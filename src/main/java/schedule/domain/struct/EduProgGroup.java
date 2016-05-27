package schedule.domain.struct;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * Группа образовательных программ. Имеет код и название, а также ссылку на
 * программы.
 */
@Entity
@Table(name = "edu_prog_group")
public class EduProgGroup {
	
	@Id
	@NotNull
	@Column(name = "edu_prog_group_code", unique = true, updatable = false)
	private int eduProgGroupCode;
	
	@Column(name = "edu_prog_group_name", updatable = false)
	@NotEmpty
	private String eduProgGroupName;
	
	@OneToMany(mappedBy = "eduProgGroup", fetch = FetchType.EAGER)
	private List<EduProgram> eduPrograms = new ArrayList<EduProgram>(0);
	
	public int getEduProgGroupCode() {
		return eduProgGroupCode;
	}
	
	public void setEduProgGroupCode(int eduProgGroupCode) {
		this.eduProgGroupCode = eduProgGroupCode;
	}
	
	public String getEduProgGroupName() {
		return eduProgGroupName;
	}
	
	public void setEduProgGroupName(String eduProgGroupName) {
		this.eduProgGroupName = eduProgGroupName;
	}
	
	public List<EduProgram> getEduPrograms() {
		return eduPrograms;
	}
	
	public void setEduPrograms(List<EduProgram> eduPrograms) {
		this.eduPrograms = eduPrograms;
	}
	
}
