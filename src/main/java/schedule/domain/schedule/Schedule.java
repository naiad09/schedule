package schedule.domain.schedule;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import schedule.domain.persons.Group;
import schedule.domain.struct.EduProcGraphic;


@Entity
@Table(name = "schedule")
public class Schedule {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_schedule", updatable = false)
	private int idSchedule;
	
	@NotNull
	@JoinColumn(name = "id_group", updatable = false)
	@ManyToOne
	private Group group;
	
	@NotNull
	@Column(name = "term_num")
	@Range(min = 1, max = 12)
	private Integer termNum;
	
	@NotNull
	@Column(name = "schedule_done")
	private boolean scheduleDone = false;
	
	@NotNull
	@JoinColumn(name = "id_edu_period")
	@ManyToOne
	private EduProcGraphic eduProcGraphic;
	
	@OneToMany(mappedBy = "schedule")
	private List<GroupLessonType> groupLessonTypes = new ArrayList<GroupLessonType>();
	
	public int getIdSchedule() {
		return idSchedule;
	}
	
	public void setIdSchedule(int idSchedule) {
		this.idSchedule = idSchedule;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public Integer getTermNum() {
		return termNum;
	}
	
	public void setTermNum(Integer termNum) {
		this.termNum = termNum;
	}
	
	public boolean isScheduleDone() {
		return scheduleDone;
	}
	
	public void setScheduleDone(boolean scheduleDone) {
		this.scheduleDone = scheduleDone;
	}
	
	public List<GroupLessonType> getGroupLessonTypes() {
		return groupLessonTypes;
	}
	
	public void setGroupLessonTypes(List<GroupLessonType> groupLessonTypes) {
		this.groupLessonTypes = groupLessonTypes;
	}
	
	public EduProcGraphic getEduProcGraphic() {
		return eduProcGraphic;
	}
	
	public void setEduProcGraphic(EduProcGraphic eduProcGraphic) {
		this.eduProcGraphic = eduProcGraphic;
	}
	
}