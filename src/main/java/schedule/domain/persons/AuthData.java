package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.access.annotation.Secured;


/**
 * Параметры авторизации: логин и пароль, а также привязка к пользователю.
 */
@Entity
@Table(name = "auth_data")
@Embeddable
public class AuthData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "auth_uid", updatable = false)
	private Integer authUid;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Person person;
	
	@Pattern(regexp = "[a-z]([a-z]|\\d)+")
	@Column(name = "login", unique = true, updatable = false)
	@Size(max = 32, min = 4)
	private String login;
	
	@Column(name = "password")
	@Size(max = 32, min = 6)
	private String password;
	
	@Column(name = "active")
	@NotNull
	private boolean active = true;
	
	@Column(name = "email")
	@Size(max = 255)
	@Email
	private String email;
	
	@Column(name = "submit")
	@NotNull
	private boolean submit = true;
	
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
	
	@Secured("ROLE_ADMIN")
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
	
	@Secured("ROLE_ADMIN")
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isSubmit() {
		return submit;
	}
	
	public void setSubmit(boolean submit) {
		this.submit = submit;
	}
	
}