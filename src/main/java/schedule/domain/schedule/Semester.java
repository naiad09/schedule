package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import schedule.domain.struct.EduProcGraphic;


/**
 * Semester generated by hbm2java
 */
@Entity
@Table(name = "semester", uniqueConstraints = @UniqueConstraint(columnNames = {
		"semester_year", "fall_spring" }))
public class Semester {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_semester", unique = true, updatable = false)@NotNull
	private int idSemester;
	
	@Column(name = "semester_year", updatable = false)
	@NotNull
	private Integer semesterYear;
	
	@Column(name = "fall_spring", updatable = false)
	@NotNull
	private boolean fallSpring;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "semester")
	private Set<EduProcGraphic> eduProcGraphics = new HashSet<EduProcGraphic>(
			0);
	
	public int getIdSemester() {
		return idSemester;
	}
	
	public void setIdSemester(int idSemester) {
		this.idSemester = idSemester;
	}
	
	public Integer getSemesterYear() {
		return semesterYear;
	}
	
	public void setSemesterYear(Integer semesterYear) {
		this.semesterYear = semesterYear;
	}
	
	public boolean isFallSpring() {
		return fallSpring;
	}
	
	public void setFallSpring(boolean fallSpring) {
		this.fallSpring = fallSpring;
	}
	
	public Set<EduProcGraphic> getEduProcGraphics() {
		return eduProcGraphics;
	}
	
	public void setEduProcGraphics(Set<EduProcGraphic> eduProcGraphics) {
		this.eduProcGraphics = eduProcGraphics;
	}
	
}
