package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * Преподаватель. Имеет ученую степень и список кафедр, на которых работает.
 */
@Entity
@Table(name = "lecturer")
@PrimaryKeyJoinColumn(name = "id_lecturer", referencedColumnName = "uid")
@Embeddable
public class Lecturer extends Person {
	
	@Enumerated(EnumType.STRING)
	@Column(name = "degree", columnDefinition = "enum(dtn,ctn)")
	private Degree degree;
	
	@OneToMany(mappedBy = "id.lecturer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Valid
	@ElementCollection(targetClass = LecturerJob.class)
	@NotEmpty
	@Embedded
	@OrderBy("jobType asc")
	private List<LecturerJob> lecturerJobs = new ArrayList<LecturerJob>();
	
	public Degree getDegree() {
		return degree;
	}
	
	public void setDegree(Degree degree) {
		this.degree = degree;
	}
	
	public List<LecturerJob> getLecturerJobs() {
		return lecturerJobs;
	}
	
	public void setLecturerJobs(List<LecturerJob> lecturerJobs) {
		this.lecturerJobs = lecturerJobs;
	}
	
	public enum Degree {
		dtn, ctn;
	}
	
}
