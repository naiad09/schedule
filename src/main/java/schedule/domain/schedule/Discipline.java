package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Дисциплина. Имеет название и номер модуля (гуманитарные, естественно-научные
 * и профессиональные).
 */
@Entity
@Table(name = "discipline")
public class Discipline {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_disc_name", unique = true, updatable = false,
			nullable = false)
	private Integer idDiscName;
	
	@Column(name = "id_disc_mod", updatable = false, nullable = false)
	private int idDiscMod;
	
	@Column(name = "disc_name", updatable = false, nullable = false)
	private String discName;
	
	public Integer getIdDiscName() {
		return idDiscName;
	}
	
	public void setIdDiscName(Integer idDiscName) {
		this.idDiscName = idDiscName;
	}
	
	public int getIdDiscMod() {
		return idDiscMod;
	}
	
	public void setIdDiscMod(int idDiscMod) {
		this.idDiscMod = idDiscMod;
	}
	
	public String getDiscName() {
		return discName;
	}
	
	public void setDiscName(String discName) {
		this.discName = discName;
	}
	
}
