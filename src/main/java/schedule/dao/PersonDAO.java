package schedule.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;

import schedule.domain.persons.AuthData;
import schedule.domain.persons.Person;


@Repository
public class PersonDAO extends GenericDAO<Person, Integer> {
	
	@Autowired
	private Md5PasswordEncoder pswEncoder;
	
	public PersonDAO() {
		super(Person.class);
	}
	
	// @Secured("ROLE_ADMIN")
	public void create(Person entity) {
		AuthData authData = entity.getAuthData();
		if (authData != null) {
			authData.setPerson(entity);
			encodePassword(authData);
		}
		super.create(entity);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or "
			+ "(isAuthenticated() and principal.uid == #entity.uid)")
	public void update(Person entity) {
		AuthData authData = entity.getAuthData();
		if (authData != null) {
			encodePassword(authData);
		}
		super.update(entity);
	}
	
	private void encodePassword(AuthData authData) {
		authData.setPassword(
				pswEncoder.encodePassword(authData.getPassword(), null));
	}
	
	@Secured("ROLE_ADMIN")
	public void delete(Person entity) {
		super.delete(entity);
	}
	
	public Person find(String username) {
		DetachedCriteria detCrit = DetachedCriteria
				.forClass(AuthData.class, "ha")
				.add(Restrictions.eq("ha.login", username))
				.setProjection(Projections.id());
		
		return (Person) currentSession().createCriteria(Person.class, "per")
				.add(Subqueries.propertyEq("per.uid", detCrit)).uniqueResult();
	}
}