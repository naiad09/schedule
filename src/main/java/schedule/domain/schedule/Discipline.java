package schedule.domain.schedule;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * Дисциплина. Имеет название и номер модуля (гуманитарные, естественно-научные
 * и профессиональные).
 */
@Entity
@Table(name = "discipline")
public class Discipline {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_disc_name", updatable = false)
	private int idDiscName;
	
	@Column(name = "id_disc_mod", updatable = false)
	@NotNull
	@Min(1)
	@Max(3)
	private int discMod;
	
	@Column(name = "disc_name", updatable = false)
	@Pattern(regexp = "[А-ЯЁA-Z][\\w\\s]+")
	@Size(max = 255, min = 5)
	@NotEmpty
	private String discName;
	
	public int getIdDiscName() {
		return idDiscName;
	}
	
	public void setIdDiscName(int idDiscName) {
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
	
	@Override
	public String toString() {
		return "Discipline [discName=" + discName + "]";
	}
	
}
