package schedule.service.converters;

import org.springframework.core.convert.converter.Converter;

import schedule.domain.struct.Group;


public class GroupConverter implements Converter<String, Group> {
	
	public Group convert(String source) {
		if (source == null || "".equals(source)) return null;
		System.out.println(source);
		Group group = new Group();
		group.setIdGroup(Integer.parseInt(source));
		return group;
	}
	
}
