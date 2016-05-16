package schedule.domain.schedule;
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
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_schedule", updatable = false, unique = true,
			nullable = false)
	private long idSchedule;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_lesson_types", updatable = false, nullable = false)
	private GroupLessonType groupLessonType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_twain", nullable = false)
	private Twain twain;
	
	@Column(name = "time_plan", nullable = false)
	private int timePlan;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "weekday", nullable = false, length = 4)
	private DayOfWeek weekday;
	
	@Column(name = "note", length = 256)
	private String note;
	
	@Column(name = "elective", nullable = false)
	private boolean elective;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "schedule_classroom",
				joinColumns = {
						@JoinColumn(name = "id_schedule", nullable = false,
									updatable = false) },
				inverseJoinColumns = {
						@JoinColumn(name = "id_classroom", nullable = false,
									updatable = false) })
	private Set<Classroom> classrooms = new HashSet<Classroom>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
	private Set<ScheduleChangeJournal> scheduleChangeJournals = new HashSet<ScheduleChangeJournal>(
			0);
	
	public long getIdSchedule() {
		return idSchedule;
	}
	
	public void setIdSchedule(long idSchedule) {
		this.idSchedule = idSchedule;
	}
	
	public GroupLessonType getGroupLessonType() {
		return groupLessonType;
	}
	
	public void setGroupLessonType(GroupLessonType groupLessonType) {
		this.groupLessonType = groupLessonType;
	}
	
	public Twain getTwain() {
		return twain;
	}
	
	public void setTwain(Twain twain) {
		this.twain = twain;
	}
	
	public int getTimePlan() {
		return timePlan;
	}
	
	public void setTimePlan(int timePlan) {
		this.timePlan = timePlan;
	}
	
	public DayOfWeek getWeekday() {
		return weekday;
	}
	
	public void setWeekday(DayOfWeek weekday) {
		this.weekday = weekday;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public boolean isElective() {
		return elective;
	}
	
	public void setElective(boolean elective) {
		this.elective = elective;
	}
	
	public Set<Classroom> getClassrooms() {
		return classrooms;
	}
	
	public void setClassrooms(Set<Classroom> classrooms) {
		this.classrooms = classrooms;
	}
	
	public Set<ScheduleChangeJournal> getScheduleChangeJournals() {
		return scheduleChangeJournals;
	}
	
	public void setScheduleChangeJournals(
			Set<ScheduleChangeJournal> scheduleChangeJournals) {
		this.scheduleChangeJournals = scheduleChangeJournals;
	}
	
}
