package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import schedule.domain.Chair.Faculty;
import schedule.domain.ScheduleChangeJournal;


/**
 * Пользователь-работник учебного отдела. Может быть привязан к фалькутету. Все
 * изменения расписания, сделанные им после утверждения оного, фиксируются в
 * журнале.
 */
@Entity
@Table(name = "edu_dep")
@PrimaryKeyJoinColumn(name = "id_edu_dep", referencedColumnName = "uid")
public class EduDep extends Person {
	
	private Faculty faculty;
	private Set<ScheduleChangeJournal> scheduleChangeJournals = new HashSet<ScheduleChangeJournal>(
			0);
	
	@Enumerated(EnumType.STRING)
	@Column(name = "id_faculty", length = 5, nullable = true, updatable = false,
			columnDefinition = "enum(vf,frt,fe,faitu,fvt,ief,hi,vi)")
	public Faculty getFaculty() {
		return this.faculty;
	}
	
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eduDep")
	public Set<ScheduleChangeJournal> getScheduleChangeJournals() {
		return this.scheduleChangeJournals;
	}
	
	public void setScheduleChangeJournals(
			Set<ScheduleChangeJournal> scheduleChangeJournals) {
		this.scheduleChangeJournals = scheduleChangeJournals;
	}
	
}
