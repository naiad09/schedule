package schedule.domain.schedule;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import schedule.domain.struct.Chair;
import schedule.domain.struct.Curriculum;


/**
 * Дисциплина в учебном плане. Имеет привязку к дисциплине, к кафедре и к
 * учебному плану. Хранит число часов лекций, лабораторных и семинаров
 * (практик), также имеет код дисциплины и важный параметр - флаг, является ли
 * дисциплина общей для всех профилей или принадлежит только одному профилю.
 */
@Entity
@Table(name = "cur_discipline")
public class CurDiscipline {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_cur_dics", updatable = false)
	private int idCurDics;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_chair", updatable = false)
	private Chair chair;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_disc_name", updatable = false)
	@NotNull
	private Discipline discipline;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_curriculum", updatable = false)
	@NotNull
	private Curriculum curriculum;
	
	@Column(name = "lecture_hours")
	@NotNull
	private short lectureHours;
	
	@Column(name = "lab_hours")
	@NotNull
	private short labHours;
	
	@Column(name = "seminar_hours")
	@NotNull
	private short seminarHours;
	
	@NotNull
	@Column(name = "disc_code", updatable = false)
	@Size(max = 16, min = 10)
	private String discCode;
	
	@Column(name = "common_profile")
	@NotNull
	private boolean commonProfile;
	
	@NotNull
	@Column(name = "variability", columnDefinition = "enum('baz','var','vib')")
	private DisciplineVariability variability;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "curDiscipline")
	private Set<DiscTerm> discTerms = new HashSet<DiscTerm>(0);
	
	public int getIdCurDics() {
		return idCurDics;
	}
	
	public void setIdCurDics(int idCurDics) {
		this.idCurDics = idCurDics;
	}
	
	public Chair getChair() {
		return chair;
	}
	
	public void setChair(Chair chair) {
		this.chair = chair;
	}
	
	public Discipline getDiscipline() {
		return discipline;
	}
	
	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}
	
	public Curriculum getCurriculum() {
		return curriculum;
	}
	
	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}
	
	public short getLectureHours() {
		return lectureHours;
	}
	
	public void setLectureHours(short lectureHours) {
		this.lectureHours = lectureHours;
	}
	
	public short getLabHours() {
		return labHours;
	}
	
	public void setLabHours(short labHours) {
		this.labHours = labHours;
	}
	
	public short getSeminarHours() {
		return seminarHours;
	}
	
	public void setSeminarHours(short seminarHours) {
		this.seminarHours = seminarHours;
	}
	
	public String getDiscCode() {
		return discCode;
	}
	
	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}
	
	public boolean isCommonProfile() {
		return commonProfile;
	}
	
	public void setCommonProfile(boolean commonProfile) {
		this.commonProfile = commonProfile;
	}
	
	public DisciplineVariability getVariability() {
		return variability;
	}
	
	public void setVariability(DisciplineVariability variability) {
		this.variability = variability;
	}
	
	public Set<DiscTerm> getDiscTerms() {
		return discTerms;
	}
	
	public void setDiscTerms(Set<DiscTerm> discTerms) {
		this.discTerms = discTerms;
	}
	
	public enum DisciplineVariability {
		baz, var, vib;
	}
}
