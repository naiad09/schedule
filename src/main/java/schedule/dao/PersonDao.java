package schedule.dao;


import org.hibernate.FetchMode;

import org.hibernate.criterion.DetachedCriteria;

import org.hibernate.criterion.Projections;

import org.hibernate.criterion.Restrictions;

import org.hibernate.criterion.Subqueries;

import org.springframework.stereotype.Component;

import org.springframework.stereotype.Repository;


import schedule.domain.persons.HttpAuth;

import schedule.domain.persons.Person;


@Component
@Repository("personDAO")
public class PersonDao extends GenericDAO<Person, Integer> {
	
	public PersonDao() {
		super(Person.class);
	}
	
	public Person read(String username) {
		DetachedCriteria detCrit = DetachedCriteria
				.forClass(HttpAuth.class, "ha")
				.add(Restrictions.eq("ha.login", username))
				.setProjection(Projections.id());
		
		return (Person) currentSession().createCriteria(Person.class, "per")
				.add(Subqueries.propertyEq("per.uid", detCrit))
				.setFetchMode("group", FetchMode.SELECT)
				.setFetchMode("lecturerJobs", FetchMode.SELECT).uniqueResult();
	}
}