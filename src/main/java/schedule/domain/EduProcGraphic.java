package schedule.domain;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * График учебного процесса. Описывает, как в каком семестре учится какое
 * направление: даты начала и окончания учебного процесса, дата смены
 * расписания, даты начала и конца зачетной и экзаменационной сессии.
 */
@Entity
@Table(name = "edu_proc_graphic")
public class EduProcGraphic {
	
	private int idEduPeriod;
	private Semester semester;
	private Date eduStart;
	private Date scheduleChangeDate;
	private Date semestrEnd;
	private Date recordSessionStart;
	private Date recordSessionEnd;
	private Date examsSessionStart;
	private Date examsSessionEnd;
	private Set<Curriculum> curriculums = new HashSet<Curriculum>(0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_edu_period", updatable = false, unique = true,
			nullable = false)
	public Integer getIdEduPeriod() {
		return this.idEduPeriod;
	}
	
	public void setIdEduPeriod(Integer idEduPeriod) {
		this.idEduPeriod = idEduPeriod;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_semestr", updatable = false, nullable = false)
	public Semester getSemester() {
		return this.semester;
	}
	
	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "edu_start", nullable = false, length = 10)
	public Date getEduStart() {
		return this.eduStart;
	}
	
	public void setEduStart(Date eduStart) {
		this.eduStart = eduStart;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "schedule_change_date", length = 10)
	public Date getScheduleChangeDate() {
		return this.scheduleChangeDate;
	}
	
	public void setScheduleChangeDate(Date scheduleChangeDate) {
		this.scheduleChangeDate = scheduleChangeDate;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "semestr_end", nullable = false, length = 10)
	public Date getSemestrEnd() {
		return this.semestrEnd;
	}
	
	public void setSemestrEnd(Date semestrEnd) {
		this.semestrEnd = semestrEnd;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "record_session_start", length = 10)
	public Date getRecordSessionStart() {
		return this.recordSessionStart;
	}
	
	public void setRecordSessionStart(Date recordSessionStart) {
		this.recordSessionStart = recordSessionStart;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "record_session_end", length = 10)
	public Date getRecordSessionEnd() {
		return this.recordSessionEnd;
	}
	
	public void setRecordSessionEnd(Date recordSessionEnd) {
		this.recordSessionEnd = recordSessionEnd;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "exams_session_start", length = 10)
	public Date getExamsSessionStart() {
		return this.examsSessionStart;
	}
	
	public void setExamsSessionStart(Date examsSessionStart) {
		this.examsSessionStart = examsSessionStart;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "exams_session_end", length = 10)
	public Date getExamsSessionEnd() {
		return this.examsSessionEnd;
	}
	
	public void setExamsSessionEnd(Date examsSessionEnd) {
		this.examsSessionEnd = examsSessionEnd;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "eduProcGraphics")
	public Set<Curriculum> getCurriculums() {
		return this.curriculums;
	}
	
	public void setCurriculums(Set<Curriculum> curriculums) {
		this.curriculums = curriculums;
	}
	
}
