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
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_classroom", unique = true, updatable = false,
			nullable = false)
	private Integer idClassroom;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_chair", updatable = false)
	private Chair chair;
	
	@Column(name = "classroom_number", unique = true, updatable = false,
			nullable = false, length = 5)
	private String classroomNumber;
	
	@Column(name = "campus", updatable = false, nullable = false)
	private int campus;
}