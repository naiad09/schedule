package schedule.domain;
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
	
	private Integer idDiscName;
	private int idDiscMod;
	private String discName;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_disc_name", unique = true, updatable = false,
			nullable = false)
	public Integer getIdDiscName() {
		return this.idDiscName;
	}
	
	public void setIdDiscName(Integer idDiscName) {
		this.idDiscName = idDiscName;
	}
	
	@Column(name = "id_disc_mod", updatable = false, nullable = false)
	public int getIdDiscMod() {
		return this.idDiscMod;
	}
	
	public void setIdDiscMod(int idDiscMod) {
		this.idDiscMod = idDiscMod;
	}
	
	@Column(name = "disc_name", updatable = false, nullable = false)
	public String getDiscName() {
		return this.discName;
	}
	
	public void setDiscName(String discName) {
		this.discName = discName;
	}
	
}
