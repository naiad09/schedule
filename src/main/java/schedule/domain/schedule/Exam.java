package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * Экзамен по Дисциплине расписания. Хранит аудиторию, дату и время консультации
 * и самого экзамена.
 */
@Entity
@Table(name = "exam")
@Embeddable
public class Exam {
	@Id
	@NotNull
	@PrimaryKeyJoinColumn(name = "id_exam")
	private int idExam;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "id_exam", updatable = false, nullable = false)
	private ScheduleDiscipline scheduleDiscipline;
	
	@ManyToOne
	@JoinColumn(name = "consult_id_classroom")
	@NotNull
	private Classroom consultClassroom;
	
	@NotNull
	@Column(name = "consult_date")
	private LocalDateTime consultDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_id_classroom")
	@NotNull
	private Classroom examClassroom;
	
	@NotNull
	@Column(name = "exam_date")
	private LocalDateTime examDate;
	
	public int getIdExam() {
		return idExam;
	}
	
	public void setIdExam(int idExam) {
		this.idExam = idExam;
	}
	
	public ScheduleDiscipline getScheduleDiscipline() {
		return scheduleDiscipline;
	}
	
	public void setScheduleDiscipline(ScheduleDiscipline scheduleDiscipline) {
		this.scheduleDiscipline = scheduleDiscipline;
	}
	
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
