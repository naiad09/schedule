package schedule.domain.schedule;

import java.util.ArrayList;
import java.util.List;

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
	private ConflictId conflictId = new ConflictId();
	
	@Column(name = "classroom_conflict")
	private boolean classroomConflict;
	
	@Column(name = "lecturer_conflict")
	private boolean lecturerConflict;
	
	public Conflict() {
		super();
	}
	
	public Conflict(ScheduleItem schiFrom, ScheduleItem schiTo) {
		conflictId = new ConflictId(schiFrom, schiTo);
	}
	
	public ConflictId getConflictId() {
		return conflictId;
	}
	
	public void setConflictId(ConflictId conflictId) {
		this.conflictId = conflictId;
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
		return conflictId.getSchiFrom();
	}
	
	public void setSchiFrom(ScheduleItem schiFrom) {
		this.conflictId.setSchiFrom(schiFrom);
	}
	
	@Transient
	public ScheduleItem getSchiTo() {
		return conflictId.getSchiTo();
	}
	
	public void setSchiTo(ScheduleItem schiTo) {
		this.conflictId.setSchiTo(schiTo);
	}
	
	@Transient
	public List<ScheduleItem> getTwoItems() {
		ArrayList<ScheduleItem> list = new ArrayList<ScheduleItem>();
		list.add(getSchiFrom());
		list.add(getSchiTo());
		return list;
	}
	
}
