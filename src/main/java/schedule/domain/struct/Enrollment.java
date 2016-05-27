package schedule.domain.struct;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import schedule.domain.struct.Curriculum.EduMode;
import schedule.domain.struct.EduProgram.EduQual;


@Entity
@Table(	name = "enrollment",
		uniqueConstraints = @UniqueConstraint(columnNames = { "edu_mode",
				"qual_type", "year_start" }))
public class Enrollment {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_enroll", unique = true, updatable = false)
	private Integer idEnroll;
	
	@NotNull
	@Column(name = "year_start", updatable = false)
	private Integer yearStart;
	
	@NotNull
	@Column(name = "edu_mode", updatable = false,
			columnDefinition = "enum(och,zao,oz)")
	@Enumerated(EnumType.STRING)
	private EduMode eduMode;
	
	@NotNull
	@Column(name = "qual_type", updatable = false,
			columnDefinition = "enum('bac','mag','spec','asp')")
	@Enumerated(EnumType.STRING)
	private EduQual eduQual;
	
	@NotNull
	@Min(2)
	@Max(6)
	@Column(name = "period_years", updatable = false)
	private Integer periodYears;
	
	@NotNull
	@Min(0)
	@Max(10)
	@Column(name = "period_months", updatable = false)
	private Integer periodMonths;
	
	@JoinTable(	name = "curriculum_semester",
				joinColumns = { @JoinColumn(name = "id_curriculum",
											updatable = false) },
				inverseJoinColumns = { @JoinColumn(	name = "id_edu_period",
													updatable = false) })
	@ManyToMany(fetch = FetchType.LAZY)
	private List<EduProcGraphic> eduProcGraphics;
	
	@OneToMany(mappedBy = "enrollment", fetch = FetchType.EAGER)
	private List<Curriculum> curriculums;
	
	public Integer getIdEnroll() {
		return idEnroll;
	}
	
	public void setIdEnroll(Integer idEnroll) {
		this.idEnroll = idEnroll;
	}
	
	public List<EduProcGraphic> getEduProcGraphics() {
		return eduProcGraphics;
	}
	
	public void setEduProcGraphics(List<EduProcGraphic> eduProcGraphics) {
		this.eduProcGraphics = eduProcGraphics;
	}
	
	public List<Curriculum> getCurriculums() {
		return curriculums;
	}
	
	public void setCurriculums(List<Curriculum> curriculums) {
		this.curriculums = curriculums;
	}
	
	public Integer getYearStart() {
		return yearStart;
	}
	
	public void setYearStart(Integer yearStart) {
		this.yearStart = yearStart;
	}
	
	public EduMode getEduMode() {
		return eduMode;
	}
	
	public void setEduMode(EduMode eduMode) {
		this.eduMode = eduMode;
	}
	
	public EduQual getEduQual() {
		return eduQual;
	}
	
	public void setEduQual(EduQual eduQual) {
		this.eduQual = eduQual;
	}
	
	@Override
	public String toString() {
		return "Enrollment [idEnroll=" + idEnroll + ", yearStart=" + yearStart
				+ ", eduMode=" + eduMode + ", eduQual=" + eduQual + "]";
	}
	
	public Integer getPeriodYears() {
		return periodYears;
	}
	
	public void setPeriodYears(Integer periodYears) {
		this.periodYears = periodYears;
	}
	
	public Integer getPeriodMonths() {
		return periodMonths;
	}
	
	public void setPeriodMonths(Integer periodMonths) {
		this.periodMonths = periodMonths;
	}
	
}