package schedule.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Repository;

import schedule.dao.util.PersonFinder;
import schedule.domain.persons.AuthData;
import schedule.domain.persons.EduDep;
import schedule.domain.persons.Lecturer;
import schedule.domain.persons.LecturerJob;
import schedule.domain.persons.Person;
import schedule.domain.persons.Student;
import schedule.service.converters.PersonConverter;


/**
 * DAO персон, наследует {@link GenericDAO}. Переопределяет методы создания и
 * обновления персоны, добавляя в них кодирование пароля. Добавляет публичные
 * методы поиска пользователя по логину и персон по критериям поиска, собранным
 * в {@link PersonFinder}.
 */
@Repository
public class PersonDAO extends GenericDAO<Person> {
	
	@Autowired
	private Md5PasswordEncoder pswEncoder;
	
	public PersonDAO() {
		super(Person.class);
	}
	
	public void saveOrUpdate(Person entity) {
		prepare(entity);
		currentSession().persist(entity);
	}
	
	public void update(Person entity) {
		prepare(entity);
		currentSession().update(entity);
	}
	
	/**
	 * Возвращает пользователя по конкретному логину, или null, если такого нет
	 */
	public Person find(String username) {
		return (Person) getCriteriaDaoType().createCriteria("authData")
				.add(Restrictions.eq("login", username))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
	}
	
	/**
	 * Подготавливает пользователя к записи в базу: проставляет двойные
	 * ассоциации, кодирует пароль.
	 */
	private void prepare(Person entity) {
		AuthData authData = entity.getAuthData();
		if (authData != null) {
			authData.setPerson(entity);
			authData.setPassword(pswEncoder.encodePassword(authData.getPassword(), null));
		}
		if (entity instanceof Lecturer) {
			Lecturer lecturer = (Lecturer) entity;
			List<LecturerJob> lecturerJobs = lecturer.getLecturerJobs().stream().distinct()
					.peek(j -> j.setLecturer(lecturer)).collect(Collectors.toList());
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
			Class<? extends Person> class1 = new PersonConverter().convert(pf.getRole()).getClass();
			crit = currentSession().createCriteria(class1);
			
			if (class1 == Student.class) {
				if (pf.getRecordBookNumber() != null) {
					crit.add(Restrictions.eq("recordBookNumber", pf.getRecordBookNumber()));
				}
			} else if (class1 == Lecturer.class) {
				if (pf.getDegree() != null) crit.add(Restrictions.eq("degree", pf.getDegree()));
				if (pf.getJobType() != null) {
					DetachedCriteria dc = DetachedCriteria.forClass(LecturerJob.class)
							.add(Restrictions.eq("jobType", pf.getJobType()))
							.setProjection(Projections.property("id.lecturer"));
					crit.add(Subqueries.propertyIn("uid", dc));
				}
			} else if (class1 == EduDep.class) {
				if (pf.getFaculty() != null) crit.add(Restrictions.eq("faculty", pf.getFaculty()));
			}
		}
		if (pf.getGender() != null) crit.add(Restrictions.eq("gender", pf.getGender()));
		if (pf.getName() != null) {
			crit.add(Restrictions.like("fullTextName", pf.getName(), MatchMode.ANYWHERE));
		}
		if (pf.getLoginExists() != null) {
			DetachedCriteria detCrit = DetachedCriteria.forClass(AuthData.class);
			detCrit.setProjection(Projections.id());
			if (pf.getLoginExists()) {
				if (pf.getLogin() != null)
					detCrit.add(Restrictions.like("login", "%" + pf.getLogin() + "%"));
				crit.add(Subqueries.propertyIn("uid", detCrit));
			} else crit.add(Subqueries.propertyNotIn("uid", detCrit));
		}
		return crit.list();
	}
	
}