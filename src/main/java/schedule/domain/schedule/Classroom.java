package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import schedule.domain.struct.Chair;


/**
 * Класс аудитории. Привязан к кафедре, имеет собственный номер и привязан к
 * корпусу.
 */
@Entity
@Table(	name = "classroom",
		uniqueConstraints = @UniqueConstraint(columnNames = { "classroom_number", "campus" }))
public class Classroom {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_classroom", unique = true, updatable = false)
	private int idClassroom;
	
	@ManyToOne
	@JoinColumn(name = "id_chair", updatable = false)
	private Chair chair;
	
	@Column(name = "classroom_number", updatable = false)
	@Size(max = 5, min = 2)
	@NotNull
	private String classroomNumber;
	
	@Column(name = "campus")
	@NotNull
	private String campus = "1";
	
	public int getIdClassroom() {
		return idClassroom;
	}
	
	public void setIdClassroom(int idClassroom) {
		this.idClassroom = idClassroom;
	}
	
	public Chair getChair() {
		return chair;
	}
	
	public void setChair(Chair chair) {
		this.chair = chair;
	}
	
	public String getClassroomNumber() {
		return classroomNumber;
	}
	
	public void setClassroomNumber(String classroomNumber) {
		this.classroomNumber = classroomNumber;
	}
	
	public String getCampus() {
		return campus;
	}
	
	public void setCampus(String campus) {
		this.campus = campus;
	}
	
	@Override
	public String toString() {
		return "a." + classroomNumber + "(" + idClassroom + ")";
	}
}