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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auth_uid", unique = true, updatable = false,
			nullable = false)
	private Integer authUid;
	
	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private Person person;
	
	@Column(name = "login", unique = true, updatable = false, nullable = false,
			length = 32)
	private String login;
	
	@Column(name = "password", nullable = false, length = 32)
	private String password;
	
	@Column(name = "active", nullable = false)
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