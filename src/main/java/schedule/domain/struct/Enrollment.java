package schedule.domain.struct;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;

import schedule.domain.curriculum.CommonCurriculum;
import schedule.domain.struct.EduProgram.EduQual;


@Entity
@Table(name = "enrollment", uniqueConstraints = @UniqueConstraint(columnNames = { "edu_mode",
		"qual_type", "year_start" }))
public class Enrollment {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_enroll", unique = true, updatable = false)
	private Integer idEnroll;
	
	@NotNull
	@Column(name = "year_start", updatable = false)
	private Integer yearStart;
	
	@NotNull
	@Column(name = "edu_mode", updatable = false, columnDefinition = "enum(och,zao,oz)")
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
	
	@Formula(value = "period_years*2+if(period_months>0,1,0)")
	private int semesterCount;
	
	@OneToMany(mappedBy = "enrollment")
	private List<CommonCurriculum> commonCurriculums;
	
	public Integer getIdEnroll() {
		return idEnroll;
	}
	
	public void setIdEnroll(Integer idEnroll) {
		this.idEnroll = idEnroll;
	}
	
	public List<CommonCurriculum> getCommonCurriculums() {
		return commonCurriculums;
	}
	
	public void setCommonCurriculums(List<CommonCurriculum> commonCurriculums) {
		this.commonCurriculums = commonCurriculums;
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
		return "Enrollment [idEnroll=" + idEnroll + ", yearStart=" + yearStart + ", eduMode="
				+ eduMode + ", eduQual=" + eduQual + "]";
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
	
	@Transient
	public int getSemesterCount() {
		return semesterCount;
	}
	
	@Transient
	public Integer getYearEnd() {
		return LocalDate.of(getYearStart(), Month.AUGUST, 1).plus(getTrainingPeriod()).getYear();
	}
	
	@Transient
	private Period getTrainingPeriod() {
		return Period.ofYears(getPeriodYears()).plusMonths(getPeriodMonths());
	}
	
	@Transient
	public Integer getCourse() {
		LocalDate start = LocalDate.of(getYearStart(), Month.AUGUST, 1);
		LocalDate now = LocalDate.now();
		Period dif = Period.between(start, now);
		
		Period trainingPeriod = getTrainingPeriod();
		
		LocalDate end = start.plus(trainingPeriod);
		
		return now.isBefore(end) ? Integer.valueOf(dif.getYears() + 1) : null;
	}
	
	public enum EduMode {
		och, zao, oz
	}
	
}