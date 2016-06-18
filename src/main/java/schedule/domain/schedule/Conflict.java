package schedule.domain.schedule;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * Класс конфликта. Содержит ссылки на оба конфликтующих элемента, а также флага
 * типов конфликтов: конфликт аудиторий и конфликт преподавателей.
 */
@Entity
@Table(name = "conflict")
public class Conflict {
	
	@EmbeddedId
	private ConflictId conflictIId = new ConflictId();
	
	public Conflict() {
		super();
	}
	
	public Conflict(ScheduleItem schiFrom, ScheduleItem schiTo) {
		conflictIId = new ConflictId(schiFrom, schiTo);
	}
	
	@Column(name = "classroom_conflict")
	public boolean classroomConflict;
	
	@Column(name = "lecturer_conflict")
	public boolean lecturerConflict;
	
	public ConflictId getConflictIId() {
		return conflictIId;
	}
	
	public void setConflictIId(ConflictId conflictIId) {
		this.conflictIId = conflictIId;
	}
	
	public boolean isClassroomConflict() {
		return classroomConflict;
	}
	
	public void setClassroomConflict(boolean classroomConflict) {
		this.classroomConflict = classroomConflict;
	}
	
	public boolean isLecturerConflict() {
		return lecturerConflict;
	}
	
	public void setLecturerConflict(boolean lecturerConflict) {
		this.lecturerConflict = lecturerConflict;
	}
	
	@Transient
	public ScheduleItem getSchiFrom() {
		return conflictIId.getSchiFrom();
	}
	
	public void setSchiFrom(ScheduleItem schiFrom) {
		this.conflictIId.setSchiFrom(schiFrom);
	}
	
	@Transient
	public ScheduleItem getSchiTo() {
		return conflictIId.getSchiTo();
	}
	
	public void setSchiTo(ScheduleItem schiTo) {
		this.conflictIId.setSchiTo(schiTo);
	}
	
}
