package schedule.domain.persons;
// Generated 08.05.2016 21:15:35 by Hibernate Tools 4.0.0

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.NotEmpty;


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
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uid", unique = true, updatable = false)
	private int uid;
	
	@OneToOne(cascade = CascadeType.ALL, optional = true, mappedBy = "person")
	@Embedded
	@Valid
	private AuthData authData;
	
	@Pattern(regexp = "[А-ЯЁ][а-яё]+(-[А-Я][а-я]+)*")
	@Column(name = "last_name", updatable = false)
	@Size(max = 64, min = 3)
	@NotEmpty
	private String lastName;
	
	@Pattern(regexp = "[А-ЯЁ][а-яё]+")
	@Column(name = "first_name", updatable = false)
	@Size(max = 32, min = 3)
	@NotEmpty
	private String firstName;
	
	@Formula(value = "concat(last_name,' ',first_name,' ',middle_name)")
	private String fullTextName;
	
	@Pattern(regexp = "[А-ЯЁ][а-яё]+")
	@Column(name = "middle_name", updatable = false)
	@Size(max = 32, min = 5)
	@NotEmpty
	private String middleName;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "gender", updatable = false,
			columnDefinition = "enum('m','f')")
	private Gender gender;
	
	@Column(name = "birthday")
	private LocalDate birthday;
	
	public int getUid() {
		return uid;
	}
	
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public AuthData getAuthData() {
		return authData;
	}
	
	public void setAuthData(AuthData authData) {
		this.authData = authData;
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
	
	@Transient
	public String getFullTextName() {
		return fullTextName;
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
		return getClass().getSimpleName().toLowerCase();
	}
	
	public enum Gender {
		m, f;
	}
	
}
