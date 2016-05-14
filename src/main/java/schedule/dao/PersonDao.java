package schedule.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import schedule.domain.persons.HttpAuth;
import schedule.domain.persons.Person;


@Repository
public class PersonDao extends SimpleGenericDAO<Person> {
	
	public PersonDao() {
		super(Person.class);
	}
	
	public HttpAuth find(String username) {
		return (HttpAuth) currentSession().createCriteria(HttpAuth.class)
				.add(Restrictions.eq("login", username)).uniqueResult();
	}
}