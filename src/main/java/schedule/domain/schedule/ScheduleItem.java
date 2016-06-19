package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Formula;


/**
 * Элемент расписания. Определяет, какая дисциплина расписания в какой день
 * недели в какую Пару и в каких Аудиториях будет вестись. Важный параметр —
 * понедельный план, который определяет, в какие недели этот элемент расписания
 * имеет место (раз в неделю, в две, в четыре недели, также с учетом смены
 * расписания). Также может содержать комментарий и флаг наличия Конфликтов с
 * другими элементами расписания.
 */
@Entity
@Table(name = "schedule_item")
public class ScheduleItem {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_schedule_item", updatable = false)
	private Long idScheduleItem;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_schedule_discipline", updatable = false, nullable = false)
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
	
	@Column(name = "comment")
	@Size(max = 512)
	private String comment;
	
	@ManyToMany
	@JoinTable(	name = "schedule_classroom",
				joinColumns = { @JoinColumn(name = "id_schedule_item", updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "id_classroom") })
	private List<Classroom> classrooms = new ArrayList<Classroom>(0);
	
	// @OneToMany(mappedBy = "schiFrom", cascade = CascadeType.ALL)
	// @Fetch(FetchMode.SUBSELECT)
	// private List<Conflict> conflicts;
	
	@Formula("exists(select * from conflict where conflict.id_schedule_item_from "
			+ "= id_schedule_item or conflict.id_schedule_item_to = id_schedule_item)")
	private boolean conflict;
	
	public Long getIdScheduleItem() {
		return idScheduleItem;
	}
	
	public void setIdScheduleItem(Long idScheduleItem) {
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
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public List<Classroom> getClassrooms() {
		return classrooms;
	}
	
	public void setClassrooms(List<Classroom> classrooms) {
		this.classrooms = classrooms;
	}
	
	public boolean isConflict() {
		return conflict;
	}
	
	public void setConflict(boolean conflict) {
		this.conflict = conflict;
	}
	
	@Override
	public String toString() {
		return "ScheduleItem [idScheduleItem=" + idScheduleItem + ", twain=" + twain.getIdTwain()
				+ ", scheduleDiscipline=" + scheduleDiscipline.getIdScheduleDiscipline()
				+ ", weekplan=" + weekplan + ", weekday=" + weekday + ", classrooms="
				+ classrooms.stream().map(c -> c.getIdClassroom()).collect(Collectors.toList())
						.toString()
				+ "]";
	}
	
}
