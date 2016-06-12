package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Класс элемента расписания. Определяет, какой предмет в какой день недели в
 * какой аудитории. Важный параметр - временной план, определяет, в какие недели
 * будет это расписание (раз в неделю, в две, в четыре недели, до смены
 * расписания, после смены).
 */
@Entity
@Table(name = "schedule_item")
public class ScheduleItem {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_schedule_item", updatable = false)
	private long idSchedule;
	
	@ManyToOne()
	@JoinColumn(name = "id_lesson_types", updatable = false)
	@NotNull
	private GroupLessonType groupLessonType;
	
	@ManyToOne()
	@JoinColumn(name = "id_twain")
	@NotNull
	private Twain twain;
	
	@Column(name = "weekplan")
	@NotNull
	// TODO mapping and test
	private short weekPlan = 255;
	
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	@Column(name = "weekday")
	private DayOfWeek weekday;
	
	@Column(name = "note")
	@Size(max = 256)
	private String note;
	
	@Column(name = "elective")
	@NotNull
	private boolean elective = false;
	
	@ManyToMany()
	@JoinTable(	name = "schedule_classroom",
				joinColumns = {
						@JoinColumn(name = "id_schedule", updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "id_classroom") })
	private List<Classroom> classrooms = new ArrayList<Classroom>(0);
	
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
	
	public short getWeekPlan() {
		return weekPlan;
	}
	
	public void setWeekPlan(short weekPlan) {
		this.weekPlan = weekPlan;
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
	
	public List<Classroom> getClassrooms() {
		return classrooms;
	}
	
	public void setClassrooms(List<Classroom> classrooms) {
		this.classrooms = classrooms;
	}
	
}
