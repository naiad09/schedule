package schedule.domain;
// Generated 09.05.2016 15:19:28 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import schedule.domain.persons.EduDep;


/**
 * Журнал изменений расписания. Имеет дату и примечание, а также флаг, является
 * ли это изменение последним в серии - после такого флага должна состояться
 * email рассылка всем пользователям, которых это изменние затрагивает. Имеет
 * ссылку на элемент расписания, а также на работника учебного отдела, который
 * это изменение совершил.
 */
@Entity
@Table(	name = "schedule_change_journal",
		uniqueConstraints = @UniqueConstraint(columnNames = { "id_schedule",
				"date_of_change" }))
public class ScheduleChangeJournal {
	
	private Integer idScheduleChange;
	private EduDep eduDep;
	private Schedule schedule;
	private String note;
	private boolean commitBool;
	private Date dateOfChange;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_schedule_change", updatable = false, unique = true,
			nullable = false)
	public Integer getIdScheduleChange() {
		return this.idScheduleChange;
	}
	
	public void setIdScheduleChange(Integer idScheduleChange) {
		this.idScheduleChange = idScheduleChange;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_edu_dep", updatable = false, nullable = false)
	public EduDep getEduDep() {
		return this.eduDep;
	}
	
	public void setEduDep(EduDep eduDep) {
		this.eduDep = eduDep;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_schedule", updatable = false, nullable = false)
	public Schedule getSchedule() {
		return this.schedule;
	}
	
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	@Column(name = "note", length = 200)
	public String getNote() {
		return this.note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(name = "commit_bool", nullable = false)
	public boolean isCommitBool() {
		return this.commitBool;
	}
	
	public void setCommitBool(boolean commitBool) {
		this.commitBool = commitBool;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_of_change", updatable = false, nullable = false,
			length = 19)
	public Date getDateOfChange() {
		return this.dateOfChange;
	}
	
	public void setDateOfChange(Date dateOfChange) {
		this.dateOfChange = dateOfChange;
	}
	
}
