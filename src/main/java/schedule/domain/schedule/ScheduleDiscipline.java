package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import schedule.domain.curriculum.SemesterDiscipline;
import schedule.domain.curriculum.Discipline;
import schedule.domain.persons.Lecturer;


/**
 * Дисциплина расписания и Вид занятий по ней, которая имеет место в данном
 * Расписании. Привязана к Расписанию, Дисциплине в семестре, имеет кеширующую
 * связь с Дисциплиной. К ней привязан список Преподавателей и список Элементов
 * расписания, а также, возможно, Экзамен.
 */
@Entity
@Table(name = "schedule_discipline")
public class ScheduleDiscipline {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_schedule_discipline", updatable = false)
	private int idScheduleDiscipline;
	
	@JoinColumn(name = "id_schedule", updatable = false, nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Schedule schedule;
	
	@JoinColumn(name = "id_disc_sem", updatable = false, nullable = false)
	@ManyToOne
	private SemesterDiscipline discTerm;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true, mappedBy = "scheduleDiscipline")
	@Embedded
	@Valid
	private Exam exam;
	
	@JoinColumn(name = "id_disc_name", updatable = false, nullable = false)
	@ManyToOne
	private Discipline disc;
	
	@ManyToMany()
	@JoinTable(	name = "lecturers_lessons",
				joinColumns = { @JoinColumn(name = "id_schedule_discipline", updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "id_lecturer") })
	private List<Lecturer> lecturers = new ArrayList<Lecturer>(0);
	
	@OneToMany(mappedBy = "scheduleDiscipline", cascade = CascadeType.ALL)
	@Valid
	@Fetch(FetchMode.SUBSELECT)
	private List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>(0);
	
	@Column(name = "lesson_type", updatable = false, nullable = false)
	@Enumerated(EnumType.STRING)
	private LessonType lessonType;
	
	public ScheduleDiscipline() {
		super();
	}
	
	public ScheduleDiscipline(Schedule schedule, SemesterDiscipline discTerm, LessonType lessonType,
			Discipline disc) {
		this.schedule = schedule;
		this.discTerm = discTerm;
		this.lessonType = lessonType;
		this.disc = disc;
	}
	
	public int getIdScheduleDiscipline() {
		return idScheduleDiscipline;
	}
	
	public void setIdScheduleDiscipline(int idScheduleDiscipline) {
		this.idScheduleDiscipline = idScheduleDiscipline;
	}
	
	public Exam getExam() {
		return exam;
	}
	
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	
	public LessonType getLessonType() {
		return lessonType;
	}
	
	public void setLessonType(LessonType lessonType) {
		this.lessonType = lessonType;
	}
	
	public List<Lecturer> getLecturers() {
		return lecturers;
	}
	
	public void setLecturers(List<Lecturer> lecturers) {
		this.lecturers = lecturers;
	}
	
	public Schedule getSchedule() {
		return schedule;
	}
	
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	public List<ScheduleItem> getScheduleItems() {
		return scheduleItems;
	}
	
	public void setScheduleItems(List<ScheduleItem> scheduleItems) {
		this.scheduleItems = scheduleItems;
	}
	
	public SemesterDiscipline getDiscTerm() {
		return discTerm;
	}
	
	public void setDiscTerm(SemesterDiscipline discTerm) {
		this.discTerm = discTerm;
	}
	
	public Discipline getDisc() {
		return disc;
	}
	
	public void setDisc(Discipline disc) {
		this.disc = disc;
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder(
				"ScheduleDiscipline [id=" + idScheduleDiscipline
						+ ", lessonType=" + lessonType + ", lecturers=" + lecturers.stream()
								.map(c -> c.getUid()).collect(Collectors.toList()).toString()
						+ "]");
		scheduleItems.forEach(s -> string.append("\n        " + s));
		return string.toString();
	}
	
	public enum LessonType {
		lec, lab, lab4, pract;
	}
}
