package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Дисциплина. Имеет название и номер модуля (гуманитарные, естественно-научные
 * и профессиональные).
 */
@Entity
@Table(name = "discipline")
public class Discipline {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_disc_name", updatable = false)
	private Integer idDiscName;
	
	@Column(name = "id_disc_mod", updatable = false)
	@NotNull
	private int discMod;
	
	@Column(name = "disc_name", updatable = false)
	@NotNull
	@Size(max = 255, min = 5)
	private String discName;
	
	public Integer getIdDiscName() {
		return idDiscName;
	}
	
	public void setIdDiscName(Integer idDiscName) {
		this.idDiscName = idDiscName;
	}
	
	public int getDiscMod() {
		return discMod;
	}
	
	public void setDiscMod(int discMod) {
		this.discMod = discMod;
	}
	
	public String getDiscName() {
		return discName;
	}
	
	public void setDiscName(String discName) {
		this.discName = discName;
	}
	
}
