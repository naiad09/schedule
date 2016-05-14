package schedule.domain.converters;

import java.time.Period;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


/**
 * Конвертер периода обучения
 */
@Converter
public class TrainingPeriodAttributeConverter
		implements AttributeConverter<Period, Float> {
	
	public Float convertToDatabaseColumn(Period trainingPeriod) {
		if (trainingPeriod == null) return null;
		
		return trainingPeriod.getYears() + trainingPeriod.getMonths() / 12f;
	}
	
	public Period convertToEntityAttribute(Float ft) {
		if (ft == null) return null;
		int years = (int) ft.floatValue();
		return Period.of(years, (int) ((ft - years) * 12), 0);
		
	}
}