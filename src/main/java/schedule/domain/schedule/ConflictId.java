package schedule.domain.schedule;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class ConflictId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JoinColumn(name = "id_schedule_item_from")
	@ManyToOne(fetch = FetchType.LAZY)
	private ScheduleItem schiFrom;
	
	@JoinColumn(name = "id_schedule_item_to")
	@ManyToOne(fetch = FetchType.LAZY)
	private ScheduleItem schiTo;
	
	public ConflictId() {}
	
	public ConflictId(ScheduleItem schiFrom, ScheduleItem schiTo) {
		if (schiFrom == schiTo) throw new IllegalArgumentException("Non conflict with itself");
		if (schiFrom.getIdScheduleItem() < schiTo.getIdScheduleItem()) {
			this.schiFrom = schiFrom;
			this.schiTo = schiTo;
		} else {
			this.schiFrom = schiTo;
			this.schiTo = schiFrom;
		}
	}
	
	public ScheduleItem getSchiFrom() {
		return schiFrom;
	}
	
	public void setSchiFrom(ScheduleItem schiFrom) {
		this.schiFrom = schiFrom;
	}
	
	public ScheduleItem getSchiTo() {
		return schiTo;
	}
	
	public void setSchiTo(ScheduleItem schiTo) {
		this.schiTo = schiTo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((schiFrom == null) ? 0 : schiFrom.hashCode());
		result = prime * result + ((schiTo == null) ? 0 : schiTo.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ConflictId other = (ConflictId) obj;
		if (schiFrom == null) {
			if (other.schiFrom != null) return false;
		} else if (!schiFrom.equals(other.schiFrom)) return false;
		if (schiTo == null) {
			if (other.schiTo != null) return false;
		} else if (!schiTo.equals(other.schiTo)) return false;
		return true;
	}
	
}