package schedule.domain.curriculum;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import schedule.domain.struct.Chair;


/**
 * Дисциплина в учебном плане. Имеет привязку к дисциплине, к кафедре и к
 * учебному плану. Хранит число часов лекций, лабораторных и семинаров (практик,
 * также имеет код дисциплины и важный параметр - флаг, является ли дисциплина
 * общей для всех профилей или принадлежит только одному профилю.
 */
@Entity
@Table(	name = "profile_discipline",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = { "id_curriculum", "id_common_disc" }),
				@UniqueConstraint(columnNames = { "id_curriculum", "id_disc_name" }) })
public class ProfileDiscipline {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_prof_disc", updatable = false)
	private int idProfDisc;
	
	@ManyToOne
	@JoinColumn(name = "id_chair", updatable = false)
	private Chair chair;
	
	@ManyToOne
	@JoinColumn(name = "id_disc_name", updatable = false, nullable = false)
	private Discipline discipline;
	
	@Column(name = "id_curriculum", updatable = false, nullable = false)
	private Integer curriculum;
	
	@ManyToOne
	@JoinColumn(name = "id_common_disc", updatable = false, nullable = false)
	private CommonDiscipline commonDiscipline;
	
	public int getIdProfDisc() {
		return idProfDisc;
	}
	
	public void setIdProfDisc(int idProfDisc) {
		this.idProfDisc = idProfDisc;
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
	
	public Integer getCurriculum() {
		return curriculum;
	}
	
	public void setCurriculum(Integer curriculum) {
		this.curriculum = curriculum;
	}
	
	public CommonDiscipline getCommonDiscipline() {
		return commonDiscipline;
	}
	
	public void setCommonDiscipline(CommonDiscipline commonDiscipline) {
		this.commonDiscipline = commonDiscipline;
	}
	
	@Override
	public String toString() {
		return "ProfileDiscipline [idProfDisc=" + idProfDisc + ", discipline=" + discipline
				+ ", curriculum=" + curriculum + ", commonDiscipline="
				+ commonDiscipline.getDiscCode() + "]";
	}
	
}
