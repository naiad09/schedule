package schedule.domain;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.time.Year;
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


/**
 * Semester generated by hbm2java
 */
@Entity
@Table(name = "semester", uniqueConstraints = @UniqueConstraint(columnNames = {
		"semester_year", "fall-spring" }))
public class Semester {
	
	private int idSemester;
	private Year semesterYear;
	private boolean fallSpring;
	private Set<EduProcGraphic> eduProcGraphics = new HashSet<EduProcGraphic>(
			0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_semester", unique = true, updatable = false,
			nullable = false)
	public int getIdSemester() {
		return this.idSemester;
	}
	
	public void setIdSemester(int idSemester) {
		this.idSemester = idSemester;
	}
	
	@Column(name = "semester_year", nullable = false, updatable = false,
			length = 0)
	public Year getSemesterYear() {
		return this.semesterYear;
	}
	
	public void setSemesterYear(Year semesterYear) {
		this.semesterYear = semesterYear;
	}
	
	@Column(name = "fall-spring", updatable = false, nullable = false)
	public boolean isFallSpring() {
		return this.fallSpring;
	}
	
	public void setFallSpring(boolean fallSpring) {
		this.fallSpring = fallSpring;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "semester")
	public Set<EduProcGraphic> getEduProcGraphics() {
		return this.eduProcGraphics;
	}
	
	public void setEduProcGraphics(Set<EduProcGraphic> eduProcGraphics) {
		this.eduProcGraphics = eduProcGraphics;
	}
	
}
