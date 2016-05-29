package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "profile_discipline", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "id_curriculum", "id_common_disc" }),
		@UniqueConstraint(columnNames = { "id_curriculum", "id_disc_name" }) })
public class ProfileDiscipline {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_prof_disc", updatable = false)
	private int idProfDisc;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_chair", updatable = false)
	private Chair chair;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_disc_name", updatable = false)
	@NotNull
	private Discipline discipline;
	
	@Column(name = "id_curriculum", updatable = false)
	private Integer curriculum;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_common_disc", updatable = false)
	@NotNull
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
	
}
