package schedule.domain.converters;

import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, String> {
	
	@Override
	public String convertToDatabaseColumn(LocalTime locTime) {
		return (locTime == null ? null : locTime.toString());
	}
	
	@Override
	public LocalTime convertToEntityAttribute(String sqlTime) {
		return (sqlTime == null ? null : LocalTime.parse(sqlTime));
	}
}
