package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import schedule.domain.curriculum.Curriculum;
import schedule.domain.schedule.Schedule;


/**
 * Класс группы. Имеет номер, учебный план и список студентов.
 */
@Entity
@Table(name = "`group`")
public class Group {
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_group", updatable = false)
	private Integer idGroup;
	
	@ManyToOne
	@JoinColumn(name = "id_curriculum", updatable = false)
	@NotNull
	private Curriculum curriculum;
	
	@NotNull
	@Column(name = "group_number", updatable = false)
	@Size(max = 5, min = 2)
	@OrderColumn
	private String groupNumber;
	
	@OneToMany(mappedBy = "group")
	@OrderBy("lastName, firstName, middleName")
	private List<Student> students = new ArrayList<Student>(0);
	
	@OneToMany(mappedBy = "group")
	private List<Schedule> schedules = new ArrayList<Schedule>(0);
	
	public Integer getIdGroup() {
		return idGroup;
	}
	
	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}
	
	public Curriculum getCurriculum() {
		return curriculum;
	}
	
	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}
	
	public String getGroupNumber() {
		return groupNumber;
	}
	
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	
	public List<Student> getStudents() {
		return students;
	}
	
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public List<Schedule> getSchedules() {
		return schedules;
	}
	
	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
	
	@Override
	public String toString() {
		return "Group [idGroup=" + idGroup + ", groupNumber=" + groupNumber + "]";
	}
	
}
