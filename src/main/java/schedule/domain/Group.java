package schedule.domain;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import schedule.domain.persons.Student;


/**
 * Класс группы. Имеет номер, учебный план, список студентов и предметов.
 */
@Entity
@Table(name = "`group`")
public class Group {
	
	private Integer idGroup;
	private Curriculum curriculum;
	private String groupNumber;
	private Set<GroupLessonType> groupLessonTypes = new HashSet<GroupLessonType>(
			0);
	private Set<Student> students = new HashSet<Student>(0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_group", unique = true, updatable = false,
			nullable = false)
	public Integer getIdGroup() {
		return this.idGroup;
	}
	
	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_curriculum", updatable = false, nullable = false)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}
	
	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}
	
	@Column(name = "group_number", updatable = false, nullable = false,
			length = 5)
	public String getGroupNumber() {
		return this.groupNumber;
	}
	
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	public Set<GroupLessonType> getGroupLessonTypes() {
		return this.groupLessonTypes;
	}
	
	public void setGroupLessonTypes(Set<GroupLessonType> groupLessonTypes) {
		this.groupLessonTypes = groupLessonTypes;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	public Set<Student> getStudents() {
		return this.students;
	}
	
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
}
