package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Параметры авторизации: логин и пароль, а также привязка к пользователю.
 */
@Entity
@Table(name = "http_auth")
public class HttpAuth {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auth_uid", updatable = false)
	private Integer authUid;
	
	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private Person person;
	
	@NotNull
	@Column(name = "login", unique = true, updatable = false)
	@Size(max = 32, min = 4)
	private String login;
	
	@NotNull
	@Column(name = "password")
	@Size(max = 32, min = 6)
	private String password;
	
	@Column(name = "active")
	private boolean active = true;
	
	public Integer getAuthUid() {
		return authUid;
	}
	
	public void setAuthUid(Integer authUid) {
		this.authUid = authUid;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
}