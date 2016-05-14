package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * Суперкласс пользователя. Может быть либо студентом, либо преподавателем, либо
 * работником учебного отдела.
 */
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
	private Integer uid;
	private HttpAuth httpAuth;
	private String lastName;
	private String firstName;
	private String middleName;
	private Gender gender;
	private LocalDate birthday;
	private String email;
	private boolean submit;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "uid", unique = true, updatable = false, nullable = false)
	public Integer getUid() {
		return this.uid;
	}
	
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "person")
	public HttpAuth getHttpAuth() {
		return httpAuth;
	}
	
	public void setHttpAuth(HttpAuth httpAuth) {
		this.httpAuth = httpAuth;
	}
	
	@Column(name = "last_name", updatable = false, nullable = false,
			length = 25)
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "first_name", updatable = false, nullable = false,
			length = 15)
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "middle_name", updatable = false, nullable = false,
			length = 20)
	public String getMiddleName() {
		return this.middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender", updatable = false, nullable = false,
			columnDefinition = "enum('m','f')")
	public Gender getGender() {
		return this.gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	@Column(name = "birthday", updatable = false, length = 10)
	public LocalDate getBirthday() {
		return this.birthday;
	}
	
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
	@Column(name = "email", length = 256)
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "submit", nullable = false)
	public boolean isSubmit() {
		return this.submit;
	}
	
	public void setSubmit(boolean submit) {
		this.submit = submit;
	}
	
	@Transient
	public String getFullName() {
		return lastName + " " + firstName.charAt(0) + ". "
				+ middleName.charAt(0) + ".";
	}
	
	@Transient
	public String getFullNameReverse() {
		return firstName.charAt(0) + ". " + middleName.charAt(0) + ". "
				+ lastName;
	}
	
	@Transient
	public String getRole() {
		return getClass().getSimpleName();
	}
	
	public enum Gender {
		m, f;
	}
	
}
