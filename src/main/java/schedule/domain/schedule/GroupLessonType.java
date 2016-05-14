package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import schedule.domain.persons.Lecturer;
import schedule.domain.struct.Group;


/**
 * Класс, определяющий, какие предметы будут вестись у какой группы в данном
 * семестре. К нему привязываетсяя расписание, а также преподаватели, которые
 * будут этот предмет вести.
 */
@Entity
@Table(name = "group_lesson_type")
@Inheritance(strategy = InheritanceType.JOINED)
public class GroupLessonType {
	
	private Integer idLessonTypes;
	private Group group;
	private DiscTerm discTerm;
	private String idLessonType;
	private Set<Lecturer> lecturers = new HashSet<Lecturer>(0);
	private Set<Schedule> schedules = new HashSet<Schedule>(0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_lesson_types", unique = true, updatable = false,
			nullable = false)
	public Integer getIdLessonTypes() {
		return this.idLessonTypes;
	}
	
	public void setIdLessonTypes(Integer idLessonTypes) {
		this.idLessonTypes = idLessonTypes;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_group", updatable = false, nullable = false)
	public Group getGroup() {
		return this.group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_disc_sem", updatable = false, nullable = false)
	public DiscTerm getDiscTerm() {
		return this.discTerm;
	}
	
	public void setDiscTerm(DiscTerm discTerm) {
		this.discTerm = discTerm;
	}
	
	@Column(name = "id_lesson_type", updatable = false, nullable = false,
			length = 5)
	public String getIdLessonType() {
		return this.idLessonType;
	}
	
	public void setIdLessonType(String idLessonType) {
		this.idLessonType = idLessonType;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "lecturers_lessons",
				joinColumns = {
						@JoinColumn(name = "id_lesson_types", nullable = false,
									updatable = false) },
				inverseJoinColumns = {
						@JoinColumn(name = "id_lecturer", nullable = false,
									updatable = false) })
	public Set<Lecturer> getLecturers() {
		return this.lecturers;
	}
	
	public void setLecturers(Set<Lecturer> lecturers) {
		this.lecturers = lecturers;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "groupLessonType")
	public Set<Schedule> getSchedules() {
		return this.schedules;
	}
	
	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}
	
}
