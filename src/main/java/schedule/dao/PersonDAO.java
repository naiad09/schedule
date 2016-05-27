package schedule.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
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
import schedule.domain.persons.EduDep;
import schedule.domain.persons.Lecturer;
import schedule.domain.persons.Person;
import schedule.domain.persons.Student;
import schedule.domain.struct.LecturerJob;
import schedule.service.PersonConverter;
import schedule.service.PersonFinder;


@Repository
public class PersonDAO extends GenericDAO<Person> {
	
	@Autowired
	private Md5PasswordEncoder pswEncoder;
	
	public PersonDAO() {
		super(Person.class);
	}
	
	public Person get(Integer key) {
		return currentSession().get(daoType, key);
	}
	
	@Secured("ROLE_ADMIN")
	public void saveOrUpdate(Person entity) {
		prepare(entity);
		currentSession().persist(entity);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or "
			+ "(isAuthenticated() and principal.uid == #entity.uid)")
	public void update(Person entity) {
		prepare(entity);
		currentSession().update(entity);
	}
	
	@Secured("ROLE_ADMIN")
	public void delete(Person entity) {
		super.delete(entity);
	}
	
	/**
	 * Возвращает пользователя по конкретному логину, или null, если такого нет
	 */
	public Person find(String username) {
		DetachedCriteria detCrit = DetachedCriteria
				.forClass(AuthData.class, "ha")
				.add(Restrictions.eq("ha.login", username))
				.setProjection(Projections.id());
		
		return (Person) currentSession().createCriteria(Person.class, "per")
				.add(Subqueries.propertyEq("per.uid", detCrit)).uniqueResult();
	}
	
	/**
	 * Подготавливает пользователя к записи в базу: проставляет двойные
	 * ассоциации, кодирует пароль.
	 */
	private void prepare(Person entity) {
		AuthData authData = entity.getAuthData();
		if (authData != null) {
			authData.setPerson(entity);
			authData.setPassword(
					pswEncoder.encodePassword(authData.getPassword(), null));
		}
		if (entity instanceof Lecturer) {
			Lecturer lecturer = (Lecturer) entity;
			List<LecturerJob> lecturerJobs = lecturer.getLecturerJobs().stream()
					.distinct().peek(j -> j.setLecturer(lecturer))
					.collect(Collectors.toList());
			lecturer.setLecturerJobs(lecturerJobs);
		}
	}
	
	/**
	 * Выполняет поиск пользователей в соответсвии с критериями, изложенными в
	 * классе PersonFinder. Используя Criteria API, добавляет критерии и
	 * составляет запрос. Крутая штука вышла.
	 */
	@SuppressWarnings("unchecked")
	public List<Person> getAll(PersonFinder pf) {
		Criteria crit;
		if (pf.getRole() == null) crit = getCriteriaDaoType();
		else {
			Class<? extends Person> class1 = new PersonConverter()
					.convert(pf.getRole()).getClass();
			crit = currentSession().createCriteria(class1);
			
			if (class1 == Student.class) {
				if (pf.getRecordBookNumber() != null) {
					crit.add(Restrictions.eq("recordBookNumber",
							pf.getRecordBookNumber()));
				}
			} else if (class1 == Lecturer.class) {
				if (pf.getDegree() != null)
					crit.add(Restrictions.eq("degree", pf.getDegree()));
				if (pf.getJobType() != null) {
					DetachedCriteria dc = DetachedCriteria
							.forClass(LecturerJob.class)
							.add(Restrictions.eq("jobType", pf.getJobType()))
							.setProjection(Projections.property("id.lecturer"));
					crit.add(Subqueries.propertyIn("uid", dc));
				}
			} else if (class1 == EduDep.class) {
				if (pf.getFaculty() != null)
					crit.add(Restrictions.eq("faculty", pf.getFaculty()));
			}
		}
		if (pf.getGender() != null)
			crit.add(Restrictions.eq("gender", pf.getGender()));
		if (pf.getName() != null) {
			crit.add(Restrictions.like("fullTextName",
					"%" + pf.getName() + "%"));
		}
		if (pf.getLoginExists() != null) {
			DetachedCriteria detCrit = DetachedCriteria
					.forClass(AuthData.class);
			detCrit.setProjection(Projections.id());
			if (pf.getLoginExists()) {
				if (pf.getLogin() != null) detCrit.add(
						Restrictions.like("login", "%" + pf.getLogin() + "%"));
				crit.add(Subqueries.propertyIn("uid", detCrit));
			} else crit.add(Subqueries.propertyNotIn("uid", detCrit));
		}
		return crit.list();
	}
}