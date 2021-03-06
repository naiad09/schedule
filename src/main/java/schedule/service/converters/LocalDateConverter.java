package schedule.service.converters;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;


/** Конвертер для байндинга объектов дат из запроса */
public class LocalDateConverter implements Converter<String, LocalDate> {
	
	public LocalDate convert(String text) {
		if ("".equals(text)) return null;
		else {
			LocalDate parse = LocalDate.parse(text);
			return parse;
		}
	}
	
}
