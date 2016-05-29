package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import schedule.domain.schedule.ScheduleChangeJournal;
import schedule.domain.struct.Chair.Faculty;


/**
 * Пользователь-работник учебного отдела. Может быть привязан к фалькутету. Все
 * изменения расписания, сделанные им после утверждения оного, фиксируются в
 * журнале.
 */
@Entity
@Table(name = "edu_dep")
@PrimaryKeyJoinColumn(name = "id_edu_dep", referencedColumnName = "uid")
@Embeddable
public class EduDep extends Person {
	
	@Enumerated(EnumType.STRING)
	@Column(name = "id_faculty", nullable = true, updatable = false,
			columnDefinition = "enum(vf,frt,fe,faitu,fvt,ief,hi,vi)")
	private Faculty faculty;
	
	@OneToMany(mappedBy = "eduDep")
	@OrderBy("dateOfChange")
	private List<ScheduleChangeJournal> scheduleChangeJournals = new ArrayList<ScheduleChangeJournal>(
			0);
	
	@Column(name = "admin")
	@NotNull
	private boolean admin = false;
	
	public Faculty getFaculty() {
		return faculty;
	}
	
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	
	public List<ScheduleChangeJournal> getScheduleChangeJournals() {
		return scheduleChangeJournals;
	}
	
	public void setScheduleChangeJournals(
			List<ScheduleChangeJournal> scheduleChangeJournals) {
		this.scheduleChangeJournals = scheduleChangeJournals;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	@NotNull
	public AuthData getAuthData() {
		return super.getAuthData();
	}
	
}
