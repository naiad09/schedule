package schedule.domain.curriculum;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * Общая дисциплина. Отражает, какие общие характеристики есть у Профильных
 * дисциплин в расписании. Хранит количество часов разных видов занятий (лекций,
 * лабораторных практик), код дисциплины, вариативность (базовая, вариативная,
 * по выбору). Связана с Общим учебным планом, имеет список Дисциплин в семестре
 * и список Профильных дисциплин.
 */
@Entity
@Table(	name = "com_discipline",
		uniqueConstraints = @UniqueConstraint(columnNames = { "id_com_cur", "disc_code" }))
public class CommonDiscipline {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_com_disc", updatable = false)
	private Integer idComDisc;
	
	@JoinColumn(name = "id_com_cur", updatable = false, nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private CommonCurriculum commonCurriculum;
	
	@NotNull
	@Column(name = "lecture_hours")
	private Short lectureHours;
	
	@NotNull
	@Column(name = "lab_hours")
	private Short labHours;
	
	@NotNull
	@Column(name = "seminar_hours")
	private Short seminarHours;
	
	@Size(max = 16, min = 10)
	@Column(name = "disc_code")
	@NotNull
	private String discCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "variability", columnDefinition = "enum('baz','var','vib')")
	@NotNull
	private DisciplineVariability variability;
	
	@OneToMany(mappedBy = "commonDiscipline")
	@Fetch(FetchMode.JOIN)
	private List<SemesterDiscipline> discTerms;
	
	@OneToMany(mappedBy = "commonDiscipline")
	@Fetch(FetchMode.SUBSELECT)
	@NotEmpty
	private List<ProfileDiscipline> profileDisciplines;
	
	public enum DisciplineVariability {
		baz, var, vib;
	}
	
	public Integer getIdComDisc() {
		return idComDisc;
	}
	
	public void setIdComDisc(Integer idComDisc) {
		this.idComDisc = idComDisc;
	}
	
	public CommonCurriculum getCommonCurriculum() {
		return commonCurriculum;
	}
	
	public void setCommonCurriculum(CommonCurriculum commonCurriculum) {
		this.commonCurriculum = commonCurriculum;
	}
	
	public Short getLectureHours() {
		return lectureHours;
	}
	
	public void setLectureHours(Short lectureHours) {
		this.lectureHours = lectureHours;
	}
	
	public Short getLabHours() {
		return labHours;
	}
	
	public void setLabHours(Short labHours) {
		this.labHours = labHours;
	}
	
	public Short getSeminarHours() {
		return seminarHours;
	}
	
	public void setSeminarHours(Short seminarHours) {
		this.seminarHours = seminarHours;
	}
	
	public String getDiscCode() {
		return discCode;
	}
	
	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}
	
	public DisciplineVariability getVariability() {
		return variability;
	}
	
	public void setVariability(DisciplineVariability variability) {
		this.variability = variability;
	}
	
	public List<SemesterDiscipline> getDiscTerms() {
		return discTerms;
	}
	
	public void setDiscTerms(List<SemesterDiscipline> discTerms) {
		this.discTerms = discTerms;
	}
	
	public List<ProfileDiscipline> getProfileDisciplines() {
		return profileDisciplines;
	}
	
	public void setProfileDisciplines(List<ProfileDiscipline> profileDisciplines) {
		this.profileDisciplines = profileDisciplines;
	}
	
	@Override
	public String toString() {
		return "ComDisc [id=" + idComDisc + ", discCode=" + discCode + ", variability="
				+ variability + "]";
	}
	
}