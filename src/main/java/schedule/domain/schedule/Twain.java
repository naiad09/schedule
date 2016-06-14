package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * Пара. Имеет время начала и конца.
 */
@Entity
@Table(name = "twain")
public class Twain {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_twain", updatable = false)
	private int idTwain;
	
	@NotNull
	@Column(name = "time_start", updatable = false)
	private LocalTime timeStart;
	
	@NotNull
	@Column(name = "time_end", updatable = false)
	private LocalTime timeEnd;
	
	public int getIdTwain() {
		return idTwain;
	}
	
	public void setIdTwain(int idTwain) {
		this.idTwain = idTwain;
	}
	
	public LocalTime getTimeStart() {
		return timeStart;
	}
	
	public void setTimeStart(LocalTime timeStart) {
		this.timeStart = timeStart;
	}
	
	public LocalTime getTimeEnd() {
		return timeEnd;
	}
	
	public void setTimeEnd(LocalTime timeEnd) {
		this.timeEnd = timeEnd;
	}
	
}
