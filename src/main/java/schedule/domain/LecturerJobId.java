package schedule.domain;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * LecturerJobId generated by hbm2java
 */
@Embeddable
public class LecturerJobId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int idLecturer;
	private int idChair;
	
	public LecturerJobId() {}
	
	public LecturerJobId(int idLecturer, int idChair) {
		this.idLecturer = idLecturer;
		this.idChair = idChair;
	}
	
	@Column(name = "id_lecturer", updatable = false, nullable = false)
	public int getIdLecturer() {
		return this.idLecturer;
	}
	
	public void setIdLecturer(int idLecturer) {
		this.idLecturer = idLecturer;
	}
	
	@Column(name = "id_chair", updatable = false, nullable = false)
	public int getIdChair() {
		return this.idChair;
	}
	
	public void setIdChair(int idChair) {
		this.idChair = idChair;
	}
	
	public boolean equals(Object other) {
		if ((this == other)) return true;
		if ((other == null)) return false;
		if (!(other instanceof LecturerJobId)) return false;
		LecturerJobId castOther = (LecturerJobId) other;
		
		return (this.getIdLecturer() == castOther.getIdLecturer())
				&& (this.getIdChair() == castOther.getIdChair());
	}
	
	public int hashCode() {
		int result = 17;
		
		result = 37 * result + this.getIdLecturer();
		result = 37 * result + this.getIdChair();
		return result;
	}
	
}
