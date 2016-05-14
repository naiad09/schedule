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

import schedule.domain.struct.Chair;


/**
 * Класс аудитории. Привязан к кафедре, имеет собственный номер и привязан к
 * корпусу.
 */
@Entity
@Table(name = "classroom")
public class Classroom {
	
	private Integer idClassroom;
	private Chair chair;
	private String classroomNumber;
	private int campus;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_classroom", unique = true, updatable = false,
			nullable = false)
	public Integer getIdClassroom() {
		return this.idClassroom;
	}
	
	public void setIdClassroom(Integer idClassroom) {
		this.idClassroom = idClassroom;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_chair", updatable = false)
	public Chair getChair() {
		return this.chair;
	}
	
	public void setChair(Chair chair) {
		this.chair = chair;
	}
	
	@Column(name = "classroom_number", unique = true, updatable = false,
			nullable = false, length = 5)
	public String getClassroomNumber() {
		return this.classroomNumber;
	}
	
	public void setClassroomNumber(String classroomNumber) {
		this.classroomNumber = classroomNumber;
	}
	
	@Column(name = "campus", updatable = false, nullable = false)
	public int getCampus() {
		return this.campus;
	}
	
	public void setCampus(int campus) {
		this.campus = campus;
	}
}