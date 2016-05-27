package schedule.dao;

import org.springframework.stereotype.Repository;

import schedule.domain.struct.EduProgGroup;


@Repository
public class EduProgGroupDAO extends MinimalGenericDAO<EduProgGroup> {
	public EduProgGroupDAO() {
		super(EduProgGroup.class);
	}
	
}
