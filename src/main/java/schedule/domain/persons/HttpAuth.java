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


/**
 * Параметры авторизации: логин и пароль, а также привязка к пользователю.
 */
@Entity
@Table(name = "http_auth")
public class HttpAuth {
	
	private Integer authUid;
	private Person person;
	private String login;
	private String password;
	private boolean active = true;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auth_uid", unique = true, updatable = false,
			nullable = false)
	public Integer getAuthUid() {
		return authUid;
	}
	
	public void setAuthUid(Integer authUid) {
		this.authUid = authUid;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	@Column(name = "login", unique = true, updatable = false, nullable = false,
			length = 32)
	public String getLogin() {
		return this.login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	@Column(name = "password", nullable = false, length = 32)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "active", nullable = false)
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
}