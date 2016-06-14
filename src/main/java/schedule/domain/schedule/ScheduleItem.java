package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private long idScheduleItem;
	
	@ManyToOne
	@JoinColumn(name = "id_schedule_discipline", updatable = false,
				nullable = false)
	private ScheduleDiscipline scheduleDiscipline;
	
	@ManyToOne
	@JoinColumn(name = "id_twain")
	@NotNull
	private Twain twain;
	
	@Column(name = "weekplan")
	@NotNull
	private Integer weekplan;
	
	@NotNull
	@Column(name = "weekday")
	private Integer weekday;
	
	@Column(name = "note")
	@Size(max = 512)
	private String note;
	
	@ManyToMany()
	@JoinTable(	name = "schedule_classroom",
				joinColumns = { @JoinColumn(name = "id_schedule_item",
											updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "id_classroom") })
	private List<Classroom> classrooms = new ArrayList<Classroom>(0);
	
	public long getIdScheduleItem() {
		return idScheduleItem;
	}
	
	public void setIdScheduleItem(long idScheduleItem) {
		this.idScheduleItem = idScheduleItem;
	}
	
	public ScheduleDiscipline getScheduleDiscipline() {
		return scheduleDiscipline;
	}
	
	public void setScheduleDiscipline(ScheduleDiscipline scheduleDiscipline) {
		this.scheduleDiscipline = scheduleDiscipline;
	}
	
	public Twain getTwain() {
		return twain;
	}
	
	public void setTwain(Twain twain) {
		this.twain = twain;
	}
	
	public Integer getWeekplan() {
		return weekplan;
	}
	
	public void setWeekplan(Integer weekplan) {
		this.weekplan = weekplan;
	}
	
	public Integer getWeekday() {
		return weekday;
	}
	
	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public List<Classroom> getClassrooms() {
		return classrooms;
	}
	
	public void setClassrooms(List<Classroom> classrooms) {
		this.classrooms = classrooms;
	}
	
}
