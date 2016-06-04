package schedule.domain.schedule;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
	@Column(name = "schedule_done")
	private boolean scheduleDone = false;
	
	@NotNull
	@JoinColumn(name = "id_edu_period")
	@ManyToOne
	private EduProcGraphic eduProcGraphic;
	
	@OneToMany(	mappedBy = "schedule", cascade = CascadeType.ALL,
				fetch = FetchType.EAGER)
	@Valid
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
	
	@Transient
	public SortedMap<DiscTerm, List<GroupLessonType>> getGroupLessonTypesMap() {
		SortedMap<DiscTerm, List<GroupLessonType>> map = new TreeMap<DiscTerm, List<GroupLessonType>>(
				new Comparator<DiscTerm>() {
					public int compare(DiscTerm o1, DiscTerm o2) {
						String s1 = o1.getCommonDiscipline().getDiscCode();
						String s2 = o2.getCommonDiscipline().getDiscCode();
						return s1.compareTo(s2);
					}
				});
		Map<DiscTerm, List<GroupLessonType>> collect = getGroupLessonTypes()
				.stream()
				.collect(Collectors.groupingBy((g) -> g.getDiscTerm()));
		map.putAll(collect);
		return map;
	}
}