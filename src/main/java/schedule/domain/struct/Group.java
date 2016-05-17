package schedule.domain.struct;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import schedule.domain.persons.Student;


/**
 * Класс группы. Имеет номер, учебный план, список студентов и предметов.
 */
@Entity
@Table(name = "`group`")
public class Group {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_group", updatable = false)
	private Integer idGroup;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_curriculum", updatable = false)
	@NotNull
	private Curriculum curriculum;
	
	@NotNull
	@Column(name = "group_number", updatable = false)
	@Size(max = 5, min = 2)
	private String groupNumber;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	private Set<Student> students = new HashSet<Student>(0);
	
	public Integer getIdGroup() {
		return idGroup;
	}
	
	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}
	
	public Curriculum getCurriculum() {
		return curriculum;
	}
	
	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}
	
	public String getGroupNumber() {
		return groupNumber;
	}
	
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	
	public Set<Student> getStudents() {
		return students;
	}
	
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
}