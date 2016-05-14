package schedule.domain;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Пара. Имеет время начала и конца.
 */
@Entity
@Table(name = "twain")
public class Twain {
	
	private int idTwain;
	private Time timeStart;
	private Time timeEnd;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_twain", updatable = false, unique = true,
			nullable = false)
	public Integer getIdTwain() {
		return this.idTwain;
	}
	
	public void setIdTwain(Integer idPeriod) {
		this.idTwain = idPeriod;
	}
	
	@Column(name = "time_start", updatable = false, nullable = false,
			length = 8)
	public Time getTimeStart() {
		return this.timeStart;
	}
	
	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}
	
	@Column(name = "time_end", updatable = false, nullable = false, length = 8)
	public Time getTimeEnd() {
		return this.timeEnd;
	}
	
	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
	}
	
}
