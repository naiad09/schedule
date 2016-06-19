package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import schedule.domain.struct.Chair.Faculty;


/**
 * Персона-Работник учебного отдела. Должностное лицо, отвечающее за составление
 * и координацию расписания. Привязан к факультету. Также хранит флаг
 * администратора.
 */
@Entity
@Table(name = "edu_dep")
@PrimaryKeyJoinColumn(name = "id_edu_dep", referencedColumnName = "uid")
@Embeddable
public class EduDep extends Person {
	
	@Enumerated(EnumType.STRING)
	@Column(name = "id_faculty", updatable = false,
			columnDefinition = "enum(vf,frt,fe,faitu,fvt,ief,hi,vi)")
	@NotNull
	private Faculty faculty;
	
	@Column(name = "admin")
	@NotNull
	private boolean admin = false;
	
	public Faculty getFaculty() {
		return faculty;
	}
	
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	@NotNull
	public AuthData getAuthData() {
		return super.getAuthData();
	}
	
}
