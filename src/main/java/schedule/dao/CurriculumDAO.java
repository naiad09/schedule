package schedule.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import schedule.domain.struct.CommonCurriculum;
import schedule.domain.struct.Curriculum;
import schedule.domain.struct.EduProgram;


@Repository
public class CurriculumDAO extends GenericDAO<CommonCurriculum> {
	public CurriculumDAO() {
		super(CommonCurriculum.class);
	}
	
	public CommonCurriculum get(Integer id) {
		return currentSession().get(daoType, id);
	}
	
	@Deprecated
	@SuppressWarnings("unchecked")
	public List<Curriculum> getCurriculumsById(Integer key) {
		SimpleExpression eq = Restrictions.eq("idCurriculum", key);
		
		DetachedCriteria oneEnroll = DetachedCriteria.forClass(Curriculum.class)
				.add(eq).setProjection(Projections.property("enrollment"));
		
		DetachedCriteria oneEduProg = DetachedCriteria
				.forClass(EduProgram.class).setProjection(Projections.id())
				.createCriteria("skillProfiles").createCriteria("curriculums")
				.add(eq).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Curriculum> list = getCriteriaDaoType()
				.add(Subqueries.propertyEq("enrollment", oneEnroll))
				.createCriteria("skillProfile").createCriteria("eduProgram")
				.add(Subqueries.propertyEq("idEduProg", oneEduProg)).list();
		
		// Object[] array = list.stream().map(c ->
		// c.getIdCurriculum()).toArray();
		//
		// currentSession().createCriteria(CurDiscipline.class)
		// .add(Restrictions.in("curriculum", array)).list();
		return list;
	}
}
