package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * Студент. Имеет номер зачетной книжки и ссылку на группу.
 */
@Entity
@Table(name = "student")
@PrimaryKeyJoinColumn(name = "id_student", referencedColumnName = "uid")
@Embeddable
public class Student extends Person {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_group")
	@NotNull
	private Group group;
	
	@Column(name = "record_book_number", updatable = false)
	@NotNull
	private Integer recordBookNumber;
	
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public Integer getRecordBookNumber() {
		return recordBookNumber;
	}
	
	public void setRecordBookNumber(Integer recordBookNumber) {
		this.recordBookNumber = recordBookNumber;
	}
	
}
