package schedule.dao.util;

import java.util.ArrayList;
import java.util.List;

import schedule.domain.schedule.ScheduleDiscipline;


/**
 * класс-слепок реального расписания. Содержит коллекцию дисциплин расписания.
 * Используется для автоматического связывания при приемке данных с формы
 * редактирования семестра.
 *
 */
public class RawSchedule {
	private List<ScheduleDiscipline> scheduleDisciplines = new ArrayList<ScheduleDiscipline>();
	
	public List<ScheduleDiscipline> getScheduleDisciplines() {
		return scheduleDisciplines;
	}
	
	public void setScheduleDisciplines(List<ScheduleDiscipline> scheduleDisciplines) {
		this.scheduleDisciplines = scheduleDisciplines;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("Raw Schedule [scheduleDisciplines:");
		scheduleDisciplines.forEach(s -> string.append("\n    " + s));
		return string.toString();
	}
	
}