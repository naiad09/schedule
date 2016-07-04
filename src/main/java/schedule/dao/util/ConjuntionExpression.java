package schedule.dao.util;

import org.hibernate.criterion.SimpleExpression;


public class ConjuntionExpression extends SimpleExpression {
	private static final long serialVersionUID = 1L;
	
	public ConjuntionExpression(String propertyName, Object value) {
		super(propertyName, value, " & ");
	}
	
}
