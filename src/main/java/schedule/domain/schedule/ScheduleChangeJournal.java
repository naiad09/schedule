package schedule.domain.schedule;
// Generated 09.05.2016 15:19:28 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import schedule.domain.persons.EduDep;


/**
 * Журнал изменений расписания. Имеет дату и примечание, а также флаг, является
 * ли это изменение последним в серии - после такого флага должна состояться
 * email рассылка всем пользователям, которых это изменние затрагивает. Имеет
 * ссылку на элемент расписания, а также на работника учебного отдела, который
 * это изменение совершил.
 */
@Entity
@Table(	name = "schedule_change_journal",
		uniqueConstraints = @UniqueConstraint(columnNames = { "id_schedule",
				"date_of_change" }))
public class ScheduleChangeJournal {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_schedule_change", updatable = false, unique = true,
			nullable = false)
	private Integer idScheduleChange;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_edu_dep", updatable = false, nullable = false)
	private EduDep eduDep;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_schedule", updatable = false, nullable = false)
	private Schedule schedule;
	
	@Column(name = "note", length = 200)
	private String note;
	
	@Column(name = "commit_bool", nullable = false)
	private boolean commitBool;
	
	@Column(name = "date_of_change", updatable = false, nullable = false,
			length = 19)
	private LocalDateTime dateOfChange;
	
}
