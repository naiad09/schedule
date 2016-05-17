package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.validation.constraints.NotNull;

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
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_lesson_types", updatable = false)
	private Integer idLessonTypes;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_group", updatable = false)
	@NotNull
	private Group group;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_disc_sem", updatable = false)
	@NotNull
	private DiscTerm discTerm;
	
	@NotNull
	@Column(name = "id_lesson_type",
			columnDefinition = "enum('lec','lab','pract','lab4')",
			updatable = false)
	@Enumerated(EnumType.STRING)
	private LessonType lessonType;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "lecturers_lessons",
				joinColumns = { @JoinColumn(name = "id_lesson_types",
											updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "id_lecturer") })
	private Set<Lecturer> lecturers = new HashSet<Lecturer>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "groupLessonType")
	private Set<Schedule> schedules = new HashSet<Schedule>(0);
	
	public Integer getIdLessonTypes() {
		return idLessonTypes;
	}
	
	public void setIdLessonTypes(Integer idLessonTypes) {
		this.idLessonTypes = idLessonTypes;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public DiscTerm getDiscTerm() {
		return discTerm;
	}
	
	public void setDiscTerm(DiscTerm discTerm) {
		this.discTerm = discTerm;
	}
	
	public LessonType getLessonType() {
		return lessonType;
	}
	
	public void setLessonType(LessonType lessonType) {
		this.lessonType = lessonType;
	}
	
	public Set<Lecturer> getLecturers() {
		return lecturers;
	}
	
	public void setLecturers(Set<Lecturer> lecturers) {
		this.lecturers = lecturers;
	}
	
	public Set<Schedule> getSchedules() {
		return schedules;
	}
	
	public void setSchedules(Set<Schedule> schedules) {
		this.schedules = schedules;
	}
	
	public enum LessonType {
		lec, lab, pract, lab4;
	}
}