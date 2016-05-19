package schedule.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import schedule.domain.persons.AuthData;
import schedule.domain.persons.Person;


@Repository
public class PersonDAO extends GenericDAO<Person, Integer> {
	
	public PersonDAO() {
		super(Person.class);
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