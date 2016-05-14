package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * Параметры авторизации: логин и пароль, а также привязка к пользователю.
 */
@Entity
@Table(name = "http_auth")
public class HttpAuth implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private Person person;
	private String login;
	private String password;
	private boolean active = true;
	
	@Id
	@Column(name = "login", unique = true, updatable = false, nullable = false,
			length = 32)
	public String getLogin() {
		return this.login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	@Column(name = "password", updatable = false, nullable = false, length = 32)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "uid", updatable = false, nullable = false)
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	@Column(name = "active", nullable = false)
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> ar = new ArrayList<GrantedAuthority>();
		ar.add(new SimpleGrantedAuthority(
				"ROLE_" + getPerson().getRole().toUpperCase()));
		return ar;
	}
	
	@Transient
	public String getUsername() {
		return getLogin();
	}
	
	@Transient
	public boolean isAccountNonExpired() {
		return isActive();
	}
	
	@Transient
	public boolean isAccountNonLocked() {
		return isActive();
	}
	
	@Transient
	public boolean isCredentialsNonExpired() {
		return isActive();
	}
	
	@Transient
	public boolean isEnabled() {
		return isActive();
	}
}