package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import java.time.LocalDateTime;

import javax.persistence.Column;import javax.persistence.FetchType;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * Exam generated by hbm2java
 */
@Entity
@Table(name = "exam")
@PrimaryKeyJoinColumn(	name = "id_lesson_types",
						referencedColumnName = "id_lesson_types")
public class Exam extends GroupLessonType {
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "consult_id_classroom")
	@NotNull
	private Classroom consultClassroom;
	
	@NotNull
	@Column(name = "consult_date")
	private LocalDateTime consultDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "exam_id_classroom")
	@NotNull
	private Classroom examClassroom;
	
	@NotNull
	@Column(name = "exam_date")
	private LocalDateTime examDate;
	
	public Classroom getConsultClassroom() {
		return consultClassroom;
	}
	
	public void setConsultClassroom(Classroom consultClassroom) {
		this.consultClassroom = consultClassroom;
	}
	
	public LocalDateTime getConsultDate() {
		return consultDate;
	}
	
	public void setConsultDate(LocalDateTime consultDate) {
		this.consultDate = consultDate;
	}
	
	public Classroom getExamClassroom() {
		return examClassroom;
	}
	
	public void setExamClassroom(Classroom examClassroom) {
		this.examClassroom = examClassroom;
	}
	
	public LocalDateTime getExamDate() {
		return examDate;
	}
	
	public void setExamDate(LocalDateTime examDate) {
		this.examDate = examDate;
	}
	
}
