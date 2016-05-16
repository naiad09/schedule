package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uid", unique = true, updatable = false, nullable = false)
	private Integer uid;
	
	@OneToOne(	fetch = FetchType.EAGER, cascade = CascadeType.ALL,
				mappedBy = "person")
	private HttpAuth httpAuth;
	
	@Column(name = "last_name", updatable = false, nullable = false,
			length = 25)
	private String lastName;
	
	@Column(name = "first_name", updatable = false, nullable = false,
			length = 15)
	private String firstName;
	
	@Column(name = "middle_name", updatable = false, nullable = false,
			length = 20)
	private String middleName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender", updatable = false, nullable = false,
			columnDefinition = "enum('m','f')")
	private Gender gender;
	
	@Column(name = "birthday", updatable = false, length = 10)
	private LocalDate birthday;
	
	@Column(name = "email", length = 256)
	private String email;
	
	@Column(name = "submit", nullable = false)
	private boolean submit;
	
	public Integer getUid() {
		return uid;
	}
	
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	public HttpAuth getHttpAuth() {
		return httpAuth;
	}
	
	public void setHttpAuth(HttpAuth httpAuth) {
		this.httpAuth = httpAuth;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}
	
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
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
