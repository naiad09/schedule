package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import schedule.domain.curriculum.DiscTerm;
import schedule.domain.curriculum.Discipline;
import schedule.domain.persons.Lecturer;


/**
 * Класс, определяющий, какие предметы будут вестись у какой группы в данном
 * семестре. К нему привязываетсяя расписание, а также преподаватели, которые
 * будут этот предмет вести.
 */
@Entity
@Table(name = "group_lesson_type")
@Inheritance(strategy = InheritanceType.JOINED)
public class GroupLessonType {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_lesson_type", updatable = false)
	private int idLessonType;
	
	@NotNull
	@JoinColumn(name = "id_schedule", updatable = false)
	@ManyToOne
	private Schedule schedule;
	
	@NotNull
	@JoinColumn(name = "id_disc_sem", updatable = false)
	@ManyToOne
	private DiscTerm discTerm;
	
	@OneToOne(	cascade = CascadeType.ALL, optional = true,
				mappedBy = "groupLessonType")
	@Embedded
	@Valid
	private Exam exam;
	
	@NotNull
	@Column(name = "lesson_type")
	@Enumerated(EnumType.STRING)
	private LessonType lessonType;
	
	@JoinColumn(name = "id_disc_name", updatable = false)
	@ManyToOne
	private Discipline disc;
	
	@ManyToMany()
	@JoinTable(	name = "lecturers_lessons",
				joinColumns = { @JoinColumn(name = "id_lesson_type",
											updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "id_lecturer") })
	private List<Lecturer> lecturers = new ArrayList<Lecturer>(0);
	
	@OneToMany(	mappedBy = "groupLessonType", cascade = CascadeType.ALL,
				fetch = FetchType.EAGER)
	@Valid
	private List<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>(0);
	
	public GroupLessonType() {
		super();
	}
	
	public GroupLessonType(Schedule schedule, DiscTerm discTerm,
			LessonType lessonType, Discipline disc) {
		this.schedule = schedule;
		this.discTerm = discTerm;
		this.lessonType = lessonType;
		this.disc = disc;
	}
	
	public int getIdLessonType() {
		return idLessonType;
	}
	
	public void setIdLessonType(int idLessonType) {
		this.idLessonType = idLessonType;
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
	
	public DiscTerm getDiscTerm() {
		return discTerm;
	}
	
	public void setDiscTerm(DiscTerm discTerm) {
		this.discTerm = discTerm;
	}
	
	public Discipline getDisc() {
		return disc;
	}
	
	public void setDisc(Discipline disc) {
		this.disc = disc;
	}
	
	@Override
	public String toString() {
		return "GroupLessonType [idLessonType=" + idLessonType + ", discTerm="
				+ discTerm + ", lessonType=" + lessonType + ", disc=" + disc
				+ "]";
	}
	
	public enum LessonType {
		lec, lab, lab4, pract;
	}
}
