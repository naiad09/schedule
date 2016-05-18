package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;


/**
 * Суперкласс пользователя. Может быть либо студентом, либо преподавателем, либо
 * работником учебного отдела.
 */
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@Embeddable
public abstract class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uid", unique = true, updatable = false)
	private Integer uid;
	
	@OneToOne(mappedBy = "person", fetch = FetchType.EAGER)
	@Embedded
	private HttpAuth httpAuth;
	
	@NotNull
	@Column(name = "last_name", updatable = false)
	@Size(max = 64, min = 5)
	private String lastName;
	
	@NotNull
	@Column(name = "first_name", updatable = false)
	@Size(max = 32, min = 3)
	private String firstName;
	
	@NotNull
	@Column(name = "middle_name", updatable = false)
	@Size(max = 32, min = 5)
	private String middleName;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "gender", updatable = false,
			columnDefinition = "enum('m','f')")
	private Gender gender;
	
	@Column(name = "birthday")
	private LocalDate birthday;
	
	@Column(name = "email")
	@Size(max = 255)
	@Email
	private String email;
	
	@Column(name = "submit")
	@NotNull
	private boolean submit = true;
	
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
