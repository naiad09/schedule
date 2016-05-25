package schedule.domain.struct;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import schedule.domain.schedule.Semester;


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
	private int idEduPeriod;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_semestr", updatable = false)
	@NotNull
	private Semester semester;
	
	@NotNull
	@Column(name = "edu_start")
	private LocalDate eduStart;
	
	@Column(name = "schedule_change_date")
	private LocalDate scheduleChangeDate;
	
	@NotNull
	@Column(name = "semestr_end")
	private LocalDate semestrEnd;
	
	@Column(name = "record_session_start")
	private LocalDate recordSessionStart;
	
	@Column(name = "record_session_end")
	private LocalDate recordSessionEnd;
	
	@Column(name = "exams_session_start")
	private LocalDate examsSessionStart;
	
	@Column(name = "exams_session_end")
	private LocalDate examsSessionEnd;
	
	@ManyToMany(mappedBy = "eduProcGraphics", fetch = FetchType.LAZY)
	private List<Curriculum> curriculums = new ArrayList<Curriculum>(0);
	
	public int getIdEduPeriod() {
		return idEduPeriod;
	}
	
	public void setIdEduPeriod(int idEduPeriod) {
		this.idEduPeriod = idEduPeriod;
	}
	
	public Semester getSemester() {
		return semester;
	}
	
	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	
	public LocalDate getEduStart() {
		return eduStart;
	}
	
	public void setEduStart(LocalDate eduStart) {
		this.eduStart = eduStart;
	}
	
	public LocalDate getScheduleChangeDate() {
		return scheduleChangeDate;
	}
	
	public void setScheduleChangeDate(LocalDate scheduleChangeDate) {
		this.scheduleChangeDate = scheduleChangeDate;
	}
	
	public LocalDate getSemestrEnd() {
		return semestrEnd;
	}
	
	public void setSemestrEnd(LocalDate semestrEnd) {
		this.semestrEnd = semestrEnd;
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
	
	public List<Curriculum> getCurriculums() {
		return curriculums;
	}
	
	public void setCurriculums(List<Curriculum> curriculums) {
		this.curriculums = curriculums;
	}
	
}
