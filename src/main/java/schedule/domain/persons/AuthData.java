package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * Параметры авторизации: логин и пароль, а также привязка к пользователю.
 */
@Entity
@Table(name = "auth_data")
@Embeddable
public class AuthData {
	
	@Id
	@NotNull
	@PrimaryKeyJoinColumn(name = "auth_uid")
	private int authUid;
	
	@OneToOne()
	@MapsId
	@JoinColumn(name = "auth_uid", updatable = false)
	private Person person;
	
	@Pattern(regexp = "[a-z][a-z\\d_-]+")
	@Column(name = "login", unique = true, updatable = false)
	@Size(max = 32, min = 4)
	@NotEmpty
	private String login;
	
	@Column(name = "password")
	@Size(max = 32, min = 6)
	@NotEmpty
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
	
	public int getAuthUid() {
		return authUid;
	}
	
	public void setAuthUid(int authUid) {
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
	
	@Override
	public String toString() {
		return "AuthData [authUid=" + authUid + ", login=" + login
				+ ", password=" + password + ", active=" + active + ", email="
				+ email + ", submit=" + submit + "]";
	}
	
}