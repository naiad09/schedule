package schedule.domain.curriculum;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import schedule.domain.semester.EduProcGraphic;
import schedule.domain.struct.EduProgram;
import schedule.domain.struct.Enrollment;


@Entity
@Table(	name = "common_curriculum",
		uniqueConstraints = @UniqueConstraint(columnNames = { "id_edu_prog", "id_enroll" }))
public class CommonCurriculum {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_common_curriculum", updatable = false)
	private Integer idCommonCurriculum;
	
	@ManyToOne
	@JoinColumn(name = "id_enroll", updatable = false, nullable = false)
	private Enrollment enrollment;
	
	@ManyToOne
	@JoinColumn(name = "id_edu_prog", updatable = false, nullable = false)
	private EduProgram eduProgram;
	
	@NotEmpty
	@OneToMany(mappedBy = "commonCurriculum")
	private List<Curriculum> curriculums = new ArrayList<Curriculum>(0);
	
	@OneToMany(mappedBy = "commonCurriculum")
	private List<CommonDiscipline> commonDisciplines = new ArrayList<CommonDiscipline>(0);
	
	@ManyToMany(mappedBy = "curriculums")
	private List<EduProcGraphic> eduProcGraphics;
	
	public Integer getIdCommonCurriculum() {
		return idCommonCurriculum;
	}
	
	public void setIdCommonCurriculum(Integer idCommonCurriculum) {
		this.idCommonCurriculum = idCommonCurriculum;
	}
	
	public Enrollment getEnrollment() {
		return enrollment;
	}
	
	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}
	
	public EduProgram getEduProgram() {
		return eduProgram;
	}
	
	public void setEduProgram(EduProgram eduProgram) {
		this.eduProgram = eduProgram;
	}
	
	public List<Curriculum> getCurriculums() {
		return curriculums;
	}
	
	public void setCurriculums(List<Curriculum> curriculums) {
		this.curriculums = curriculums;
	}
	
	public List<CommonDiscipline> getCommonDisciplines() {
		return commonDisciplines;
	}
	
	public void setCommonDisciplines(List<CommonDiscipline> commonDisciplines) {
		this.commonDisciplines = commonDisciplines;
	}
	
	public List<EduProcGraphic> getEduProcGraphics() {
		return eduProcGraphics;
	}
	
	public void setEduProcGraphics(List<EduProcGraphic> eduProcGraphics) {
		this.eduProcGraphics = eduProcGraphics;
	}
	
	@Override
	public String toString() {
		return super.toString() + "[idCommonCurriculum=" + idCommonCurriculum + ", enrollment="
				+ enrollment + "]";
	}
	
}