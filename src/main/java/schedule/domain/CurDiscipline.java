package schedule.domain;
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


/**
 * Дисциплина в учебном плане. Имеет привязку к дисциплине, к кафедре и к
 * учебному плану. Хранит число часов лекций, лабораторных и семинаров
 * (практик), также имеет код дисциплины и важный параметр - флаг, является ли
 * дисциплина общей для всех профилей или принадлежит только одному профилю.
 */
@Entity
@Table(name = "cur_discipline")
public class CurDiscipline {
	
	private int idCurDics;
	private Chair chair;
	private Discipline discipline;
	private Curriculum curriculum;
	private short lectureHours;
	private short labHours;
	private short seminarHours;
	private String discCode;
	private boolean commonProfile;
	private String variability;
	private Set<DiscTerm> discTerms = new HashSet<DiscTerm>(0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_cur_dics", updatable = false, unique = true,
			nullable = false)
	public Integer getIdCurDics() {
		return this.idCurDics;
	}
	
	public void setIdCurDics(Integer idCurDics) {
		this.idCurDics = idCurDics;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_chair", updatable = false)
	public Chair getChair() {
		return this.chair;
	}
	
	public void setChair(Chair chair) {
		this.chair = chair;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_disc_name", updatable = false, nullable = false)
	public Discipline getDiscipline() {
		return this.discipline;
	}
	
	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_curriculum", updatable = false, nullable = false)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}
	
	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}
	
	@Column(name = "lecture_hours", nullable = false)
	public short getLectureHours() {
		return this.lectureHours;
	}
	
	public void setLectureHours(short lectureHours) {
		this.lectureHours = lectureHours;
	}
	
	@Column(name = "lab_hours", nullable = false)
	public short getLabHours() {
		return this.labHours;
	}
	
	public void setLabHours(short labHours) {
		this.labHours = labHours;
	}
	
	@Column(name = "seminar_hours", nullable = false)
	public short getSeminarHours() {
		return this.seminarHours;
	}
	
	public void setSeminarHours(short seminarHours) {
		this.seminarHours = seminarHours;
	}
	
	@Column(name = "disc_code", nullable = false, updatable = false,
			length = 16)
	public String getDiscCode() {
		return this.discCode;
	}
	
	public void setDiscCode(String discCode) {
		this.discCode = discCode;
	}
	
	@Column(name = "common-profile", nullable = false)
	public boolean isCommonProfile() {
		return this.commonProfile;
	}
	
	public void setCommonProfile(boolean commonProfile) {
		this.commonProfile = commonProfile;
	}
	
	@Column(name = "variability", nullable = false, length = 4)
	public String getVariability() {
		return this.variability;
	}
	
	public void setVariability(String variability) {
		this.variability = variability;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "curDiscipline")
	public Set<DiscTerm> getDiscTerms() {
		return this.discTerms;
	}
	
	public void setDiscTerms(Set<DiscTerm> discTerms) {
		this.discTerms = discTerms;
	}
	
}
