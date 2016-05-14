package schedule.domain;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Класс элемента расписания. Определяет, какой предмет в какой день недели в
 * какой аудитории. Важный параметр - временной план, определяет, в какие недели
 * будет это расписание (раз в неделю, в две, в четыре недели, до смены
 * расписания, после смены).
 */
@Entity
@Table(name = "schedule")
public class Schedule {
	
	private long idSchedule;
	private GroupLessonType groupLessonType;
	private Twain twain;
	private int timePlan;
	private DayOfWeek weekday;
	private String note;
	private boolean elective;
	private Set<Classroom> classrooms = new HashSet<Classroom>(0);
	private Set<ScheduleChangeJournal> scheduleChangeJournals = new HashSet<ScheduleChangeJournal>(
			0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_schedule", updatable = false, unique = true,
			nullable = false)
	public long getIdSchedule() {
		return this.idSchedule;
	}
	
	public void setIdSchedule(long idSchedule) {
		this.idSchedule = idSchedule;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_lesson_types", updatable = false, nullable = false)
	public GroupLessonType getGroupLessonType() {
		return this.groupLessonType;
	}
	
	public void setGroupLessonType(GroupLessonType groupLessonType) {
		this.groupLessonType = groupLessonType;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_twain", nullable = false)
	public Twain getTwain() {
		return this.twain;
	}
	
	public void setTwain(Twain twain) {
		this.twain = twain;
	}
	
	@Column(name = "time_plan", nullable = false)
	public int getTimePlan() {
		return this.timePlan;
	}
	
	public void setTimePlan(int timePlan) {
		this.timePlan = timePlan;
	}
	
	// TODO
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "weekday", nullable = false, length = 4)
	public DayOfWeek getWeekday() {
		return this.weekday;
	}
	
	public void setWeekday(DayOfWeek weekday) {
		this.weekday = weekday;
	}
	
	@Column(name = "note", length = 256)
	public String getNote() {
		return this.note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(name = "elective", nullable = false)
	public boolean isElective() {
		return this.elective;
	}
	
	public void setElective(boolean elective) {
		this.elective = elective;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "schedule_classroom",
				joinColumns = {
						@JoinColumn(name = "id_schedule", nullable = false,
									updatable = false) },
				inverseJoinColumns = {
						@JoinColumn(name = "id_classroom", nullable = false,
									updatable = false) })
	public Set<Classroom> getClassrooms() {
		return this.classrooms;
	}
	
	public void setClassrooms(Set<Classroom> classrooms) {
		this.classrooms = classrooms;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
	public Set<ScheduleChangeJournal> getScheduleChangeJournals() {
		return this.scheduleChangeJournals;
	}
	
	public void setScheduleChangeJournals(
			Set<ScheduleChangeJournal> scheduleChangeJournals) {
		this.scheduleChangeJournals = scheduleChangeJournals;
	}
	
}
