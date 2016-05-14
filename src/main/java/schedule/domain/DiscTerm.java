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


/**
 * Дисцпилина учебного плана по семестрам. Детализирует, сколько часов в каком
 * семестре в неделю. Также имеется флаг экзамена по данной дисциплине.
 */
@Entity
@Table(name = "disc_term")
public class DiscTerm {
	
	private int idDiscSem;
	private CurDiscipline curDiscipline;
	private float hoursHerWeek;
	private int termNum;
	private boolean exam;
	private Set<GroupLessonType> groupLessonTypes = new HashSet<GroupLessonType>(
			0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_disc_sem", unique = true, updatable = false,
			nullable = false)
	public Integer getIdDiscSem() {
		return this.idDiscSem;
	}
	
	public void setIdDiscSem(Integer idDiscSem) {
		this.idDiscSem = idDiscSem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cur_dics", updatable = false, nullable = false)
	public CurDiscipline getCurDiscipline() {
		return this.curDiscipline;
	}
	
	public void setCurDiscipline(CurDiscipline curDiscipline) {
		this.curDiscipline = curDiscipline;
	}
	
	@Column(name = "hours_her_week", nullable = false, precision = 12,
			scale = 0)
	public float getHoursHerWeek() {
		return this.hoursHerWeek;
	}
	
	public void setHoursHerWeek(float hoursHerWeek) {
		this.hoursHerWeek = hoursHerWeek;
	}
	
	@Column(name = "term_num", nullable = false)
	public int getTermNum() {
		return this.termNum;
	}
	
	public void setTermNum(int termNum) {
		this.termNum = termNum;
	}
	
	@Column(name = "exam", nullable = false)
	public boolean isExam() {
		return this.exam;
	}
	
	public void setExam(boolean exam) {
		this.exam = exam;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discTerm")
	public Set<GroupLessonType> getGroupLessonTypes() {
		return this.groupLessonTypes;
	}
	
	public void setGroupLessonTypes(Set<GroupLessonType> groupLessonTypes) {
		this.groupLessonTypes = groupLessonTypes;
	}
	
}
