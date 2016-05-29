package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * Дисцпилина учебного плана по семестрам. Детализирует, сколько часов в каком
 * семестре в неделю. Также имеется флаг экзамена по данной дисциплине.
 */
@Entity
@Table(name = "disc_term")
public class DiscTerm {
	
	@Id@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_disc_sem", updatable = false)
	private int idDiscSem;
	
	@ManyToOne()
	@JoinColumn(name = "id_com_disc", updatable = false)
	@NotNull
	private CommonDiscipline commonDiscipline;
	
	@NotNull
	@Column(name = "hours_her_week")
	@Min(1)
	@Max(20)
	private Float hoursHerWeek;
	
	@Column(name = "term_num")
	@NotNull
	@Min(1)
	@Max(12)
	private int termNum;
	
	@Column(name = "exam")
	@NotNull
	private boolean exam;
	
	@OneToMany(mappedBy = "discTerm")
	private List<GroupLessonType> groupLessonTypes = new ArrayList<GroupLessonType>(
			0);
	
	public int getIdDiscSem() {
		return idDiscSem;
	}
	
	public void setIdDiscSem(int idDiscSem) {
		this.idDiscSem = idDiscSem;
	}
	
	public CommonDiscipline getCommonDiscipline() {
		return commonDiscipline;
	}
	
	public void setCommonDiscipline(CommonDiscipline commonDiscipline) {
		this.commonDiscipline = commonDiscipline;
	}
	
	public Float getHoursHerWeek() {
		return hoursHerWeek;
	}
	
	public void setHoursHerWeek(Float hoursHerWeek) {
		this.hoursHerWeek = hoursHerWeek;
	}
	
	public int getTermNum() {
		return termNum;
	}
	
	public void setTermNum(int termNum) {
		this.termNum = termNum;
	}
	
	public boolean isExam() {
		return exam;
	}
	
	public void setExam(boolean exam) {
		this.exam = exam;
	}
	
	public List<GroupLessonType> getGroupLessonTypes() {
		return groupLessonTypes;
	}
	
	public void setGroupLessonTypes(List<GroupLessonType> groupLessonTypes) {
		this.groupLessonTypes = groupLessonTypes;
	}
	
}
