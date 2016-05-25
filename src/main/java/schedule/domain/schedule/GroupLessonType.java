package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
	private int idLessonTypes;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_group", updatable = false)
	@NotNull
	private Group group;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_disc_sem", updatable = false)
	@NotNull
	private DiscTerm discTerm;
	
	@NotNull
	@Column(name = "id_lesson_type",
			columnDefinition = "enum('lec','lab','pract','lab4')",
			updatable = false)
	@Enumerated(EnumType.STRING)
	private LessonType lessonType;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(	name = "lecturers_lessons",
				joinColumns = { @JoinColumn(name = "id_lesson_types",
											updatable = false) },
				inverseJoinColumns = { @JoinColumn(name = "id_lecturer") })
	private List<Lecturer> lecturers = new ArrayList<Lecturer>(0);
	
	@OneToMany(mappedBy = "groupLessonType",fetch=FetchType.LAZY)
	private List<Schedule> schedules = new ArrayList<Schedule>(0);
	
	public int getIdLessonTypes() {
		return idLessonTypes;
	}
	
	public void setIdLessonTypes(int idLessonTypes) {
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
	
	public List<Lecturer> getLecturers() {
		return lecturers;
	}
	
	public void setLecturers(List<Lecturer> lecturers) {
		this.lecturers = lecturers;
	}
	
	public List<Schedule> getSchedules() {
		return schedules;
	}
	
	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
	
	public enum LessonType {
		lec, lab, pract, lab4;
	}
}
