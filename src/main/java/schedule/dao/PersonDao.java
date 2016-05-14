package schedule.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import schedule.domain.persons.EduDep;
import schedule.domain.persons.HttpAuth;
import schedule.domain.persons.Lecturer;
import schedule.domain.persons.Person;
import schedule.domain.persons.Student;


@Repository
public class PersonDao extends SimpleGenericDAO<Person> {
	
	public PersonDao() {
		super(Person.class);
	}
	
	public Person getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Role role = Role.valueOf(auth.getAuthorities().toArray()[0].toString());
		
		if (Role.ROLE_ANONYMOUS == role) return null;
		
		DetachedCriteria crit = DetachedCriteria.forClass(HttpAuth.class)
				.add(Restrictions.eq("login", auth.getName()))
				.setProjection(Projections.id());
		
		return (Person) currentSession().createCriteria(role.personType, "per")
				.add(Subqueries.propertyEq("per.httpAuth", crit))
				.uniqueResult();
		
	}
	
	public HttpAuth find(String username) {
		return (HttpAuth) currentSession().createCriteria(HttpAuth.class)
				.add(Restrictions.eq("login", username)).uniqueResult();
	}
	
	public enum Role {
		ROLE_ANONYMOUS(null), ROLE_STUDENT(Student.class),
		ROLE_LECTURER(Lecturer.class), ROLE_EDUDEP(EduDep.class);
		private Class<? extends Person> personType;
		
		private Role(Class<? extends Person> personType) {
			this.personType = personType;
		}
		
	}
}