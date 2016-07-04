package schedule.domain.semester;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import schedule.domain.curriculum.CommonCurriculum;
import schedule.domain.schedule.Schedule;
import schedule.domain.struct.Enrollment;


/**
 * График учебного процесса. Описывает, как в каком семестре учится какое
 * направление: даты начала и окончания учебного процесса, дата смены
 * расписания, даты начала и конца зачетной и экзаменационной сессии.
 */
@Entity
@Table(name = "edu_proc_graphic")
public class EduProcGraphic {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_edu_period", updatable = false, unique = true)
	private Integer idEduPeriod;
	
	@ManyToOne
	@JoinColumn(name = "id_semestr", updatable = false, nullable = false)
	private Semester semester;
	
	@JoinTable(	name = "curriculum_semester",
				joinColumns = @JoinColumn(name = "id_edu_period", updatable = false),
				inverseJoinColumns = @JoinColumn(name = "id_common_curriculum"))
	@ManyToMany
	private List<CommonCurriculum> curriculums = new ArrayList<CommonCurriculum>(0);
	
	@Transient
	private Enrollment enroll;
	
	@OneToMany(mappedBy = "eduProcGraphic")
	private List<Schedule> schedules = new ArrayList<>();
	
	@NotNull
	@Column(name = "semester_start")
	private LocalDate semesterStart;
	
	@Column(name = "schedule_change")
	private LocalDate scheduleChange;
	
	@NotNull
	@Column(name = "semester_end")
	private LocalDate semesterEnd;
	
	@Column(name = "record_session_start")
	private LocalDate recordSessionStart;
	
	@Column(name = "record_session_end")
	private LocalDate recordSessionEnd;
	
	@Column(name = "exams_session_start")
	private LocalDate examsSessionStart;
	
	@Column(name = "exams_session_end")
	private LocalDate examsSessionEnd;
	
	public Integer getIdEduPeriod() {
		return idEduPeriod;
	}
	
	public void setIdEduPeriod(Integer idEduPeriod) {
		this.idEduPeriod = idEduPeriod;
	}
	
	public Semester getSemester() {
		return semester;
	}
	
	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	
	public LocalDate getSemesterStart() {
		return semesterStart;
	}
	
	public void setSemesterStart(LocalDate semesterStart) {
		this.semesterStart = semesterStart;
	}
	
	public LocalDate getScheduleChange() {
		return scheduleChange;
	}
	
	public void setScheduleChange(LocalDate scheduleChange) {
		this.scheduleChange = scheduleChange;
	}
	
	public LocalDate getSemesterEnd() {
		return semesterEnd;
	}
	
	public void setSemesterEnd(LocalDate semesterEnd) {
		this.semesterEnd = semesterEnd;
	}
	
	public LocalDate getRecordSessionStart() {
		return recordSessionStart;
	}
	
	public void setRecordSessionStart(LocalDate recordSessionStart) {
		this.recordSessionStart = recordSessionStart;
	}
	
	public LocalDate getRecordSessionEnd() {
		return recordSessionEnd;
	}
	
	public void setRecordSessionEnd(LocalDate recordSessionEnd) {
		this.recordSessionEnd = recordSessionEnd;
	}
	
	public LocalDate getExamsSessionStart() {
		return examsSessionStart;
	}
	
	public void setExamsSessionStart(LocalDate examsSessionStart) {
		this.examsSessionStart = examsSessionStart;
	}
	
	public LocalDate getExamsSessionEnd() {
		return examsSessionEnd;
	}
	
	public void setExamsSessionEnd(LocalDate examsSessionEnd) {
		this.examsSessionEnd = examsSessionEnd;
	}
	
	public List<CommonCurriculum> getCurriculums() {
		return curriculums;
	}
	
	public void setCurriculums(List<CommonCurriculum> curriculums) {
		this.curriculums = curriculums;
	}
	
	public Enrollment getEnroll() {
		return enroll;
	}
	
	public void setEnroll(Enrollment enroll) {
		this.enroll = enroll;
	}
	
	public List<Schedule> getSchedules() {
		return schedules;
	}
	
	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
	
	@Override
	public String toString() {
		return "EduProcGraphic [idEduPeriod=" + idEduPeriod + ", semester=" + semester
				+ ", semesterStart=" + semesterStart + ", scheduleChange=" + scheduleChange
				+ ", semesterEnd=" + semesterEnd + ", recordSessionStart=" + recordSessionStart
				+ ", recordSessionEnd=" + recordSessionEnd + ", examsSessionStart="
				+ examsSessionStart + ", examsSessionEnd=" + examsSessionEnd + ", curriculums="
				+ curriculums + ", enroll=" + enroll + "]";
	}
	
}
