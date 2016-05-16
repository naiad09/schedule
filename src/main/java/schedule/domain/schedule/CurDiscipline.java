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
	@Column(name = "id_cur_dics", updatable = false, unique = true,
			nullable = false)
	private int idCurDics;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_chair", updatable = false)
	private Chair chair;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_disc_name", updatable = false, nullable = false)
	private Discipline discipline;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_curriculum", updatable = false, nullable = false)
	private Curriculum curriculum;
	
	@Column(name = "lecture_hours", nullable = false)
	private short lectureHours;
	
	@Column(name = "lab_hours", nullable = false)
	private short labHours;
	
	@Column(name = "seminar_hours", nullable = false)
	private short seminarHours;
	
	@Column(name = "disc_code", nullable = false, updatable = false,
			length = 16)
	private String discCode;
	
	@Column(name = "common-profile", nullable = false)
	private boolean commonProfile;
	
	@Column(name = "variability", nullable = false, length = 4)
	private String variability;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "curDiscipline")
	private Set<DiscTerm> discTerms = new HashSet<DiscTerm>(0);
	
}
