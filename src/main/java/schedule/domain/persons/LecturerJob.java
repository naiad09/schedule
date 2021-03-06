package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import schedule.domain.struct.Chair;


/**
 * Работа преподавателя. Связывает преподавателя и кафедру, также устанавливая
 * должность, на которой преподаватель работает на данной кафедре.
 */
@Entity
@Table(name = "lecturer_job")
@Embeddable
public class LecturerJob {
	
	@EmbeddedId
	private LecturerJobId id = new LecturerJobId();
	
	@Enumerated(EnumType.STRING)
	@Column(name = "id_job", columnDefinition = "enum('pr','doc','stp')")
	@NotNull
	private JobType jobType;
	
	public LecturerJobId getId() {
		return id;
	}
	
	public void setId(LecturerJobId id) {
		this.id = id;
	}
	
	@Transient
	public Chair getChair() {
		return getId().getChair();
	}
	
	public void setChair(Chair chair) {
		getId().setChair(chair);
	}
	
	@Transient
	public Lecturer getLecturer() {
		return getId().getLecturer();
	}
	
	public void setLecturer(Lecturer lecturer) {
		getId().setLecturer(lecturer);
	}
	
	public JobType getJobType() {
		return jobType;
	}
	
	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}
	
	public enum JobType {
		pr, doc, stp;
	}
	
}
