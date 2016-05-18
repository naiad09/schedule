package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;import javax.persistence.FetchType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import schedule.domain.struct.LecturerJob;


/**
 * Lecturer generated by hbm2java
 */
@Entity
@Table(name = "lecturer")
@PrimaryKeyJoinColumn(name = "id_lecturer", referencedColumnName = "uid")
@Embeddable
public class Lecturer extends Person {
	
	@Enumerated(EnumType.STRING)
	@Column(name = "degree", columnDefinition = "enum(dtn,ctn)")
	@NotNull
	private Degree degree;
	
	@OneToMany(mappedBy = "lecturer",fetch=FetchType.LAZY)
	private Set<LecturerJob> lecturerJobs = new HashSet<LecturerJob>(0);
	
	public Degree getDegree() {
		return degree;
	}
	
	public void setDegree(Degree degree) {
		this.degree = degree;
	}
	
	public Set<LecturerJob> getLecturerJobs() {
		return lecturerJobs;
	}
	
	public void setLecturerJobs(Set<LecturerJob> lecturerJobs) {
		this.lecturerJobs = lecturerJobs;
	}
	
	public enum Degree {
		dtn, ctn;
	}
	
}
