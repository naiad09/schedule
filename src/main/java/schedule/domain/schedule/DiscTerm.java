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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * Дисцпилина учебного плана по семестрам. Детализирует, сколько часов в каком
 * семестре в неделю. Также имеется флаг экзамена по данной дисциплине.
 */
@Entity
@Table(name = "disc_term")
public class DiscTerm {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_disc_sem", updatable = false)
	private int idDiscSem;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cur_dics", updatable = false)
	@NotNull
	private CurDiscipline curDiscipline;
	
	@NotNull
	@Column(name = "hours_her_week")
	private float hoursHerWeek;
	
	@Column(name = "term_num")
	@NotNull
	private int termNum;
	
	@Column(name = "exam")
	@NotNull
	private boolean exam;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discTerm")
	private Set<GroupLessonType> groupLessonTypes = new HashSet<GroupLessonType>(
			0);
	
	public int getIdDiscSem() {
		return idDiscSem;
	}
	
	public void setIdDiscSem(int idDiscSem) {
		this.idDiscSem = idDiscSem;
	}
	
	public CurDiscipline getCurDiscipline() {
		return curDiscipline;
	}
	
	public void setCurDiscipline(CurDiscipline curDiscipline) {
		this.curDiscipline = curDiscipline;
	}
	
	public float getHoursHerWeek() {
		return hoursHerWeek;
	}
	
	public void setHoursHerWeek(float hoursHerWeek) {
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
	
	public Set<GroupLessonType> getGroupLessonTypes() {
		return groupLessonTypes;
	}
	
	public void setGroupLessonTypes(Set<GroupLessonType> groupLessonTypes) {
		this.groupLessonTypes = groupLessonTypes;
	}
	
}
