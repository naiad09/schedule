package schedule.dao;

import org.springframework.stereotype.Repository;

import schedule.domain.persons.Group;


@Repository
public class GroupDAO extends GenericDAO<Group> {
	
	public GroupDAO() {
		super(Group.class);
	}
	
}
