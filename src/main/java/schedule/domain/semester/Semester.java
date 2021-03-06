package schedule.domain.semester;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Семестр. Хранит год и флаг, определяющий полугодие.
 */
@Entity
@Table(	name = "semester",
		uniqueConstraints = @UniqueConstraint(columnNames = { "semester_year", "fall_spring" }))
public class Semester {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_semester", unique = true, updatable = false)
	@NotNull
	private Integer idSemester;
	
	@Column(name = "semester_year", updatable = false)
	@NotNull
	private Integer semesterYear;
	
	@Column(name = "fall_spring", updatable = false)
	@NotNull
	private Boolean fallSpring;
	
	@OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
	@Valid
	private List<EduProcGraphic> eduProcGraphics = new ArrayList<EduProcGraphic>(0);
	
	public Integer getIdSemester() {
		return idSemester;
	}
	
	public void setIdSemester(Integer idSemester) {
		this.idSemester = idSemester;
	}
	
	public Integer getSemesterYear() {
		return semesterYear;
	}
	
	public void setSemesterYear(Integer semesterYear) {
		this.semesterYear = semesterYear;
	}
	
	public Boolean getFallSpring() {
		return fallSpring;
	}
	
	public void setFallSpring(Boolean fallSpring) {
		this.fallSpring = fallSpring;
	}
	
	public List<EduProcGraphic> getEduProcGraphics() {
		return eduProcGraphics;
	}
	
	public void setEduProcGraphics(List<EduProcGraphic> eduProcGraphics) {
		this.eduProcGraphics = eduProcGraphics;
	}
	
	public static Semester calcCurrentSemester() {
		LocalDate now = LocalDate.now();
		boolean fallSpring;
		int year = now.getYear();
		if (now.isAfter(LocalDate.of(year, Month.AUGUST, 31))
				|| now.isBefore(LocalDate.of(year, Month.JANUARY, 31)))
			fallSpring = false;
		else fallSpring = true;
		
		Semester semester = new Semester();
		semester.semesterYear = fallSpring ? year - 1 : year;
		semester.fallSpring = fallSpring;
		return semester;
	}
	
	public static Semester calcNextSemester(Semester one) {
		Semester semester = new Semester();
		semester.semesterYear = one.fallSpring ? one.semesterYear : (one.semesterYear + 1);
		semester.fallSpring = !one.fallSpring;
		return semester;
	}
	
	@Override
	public String toString() {
		return "Semester [semesterYear=" + semesterYear + ", fallSpring=" + fallSpring + "]";
	}
	
}
