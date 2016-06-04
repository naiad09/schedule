package schedule.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import schedule.domain.persons.Lecturer.Degree;
import schedule.domain.persons.LecturerJob.JobType;
import schedule.domain.schedule.CommonDiscipline.DisciplineVariability;
import schedule.domain.struct.Chair.Faculty;
import schedule.domain.struct.EduProcGraphic;
import schedule.domain.struct.EduProgram.EduQual;
import schedule.domain.struct.Enrollment.EduMode;


@Service
public class RefsContainer {
	
	public EduQual[] getQualTypes() {
		return EduQual.values();
	}
	
	public JobType[] getJobTypes() {
		return JobType.values();
	}
	
	public EduMode[] getEduModes() {
		return EduMode.values();
	}
	
	public Degree[] getDegrees() {
		return Degree.values();
	}
	
	public Faculty[] getFaculties() {
		return Faculty.values();
	}
	
	public DisciplineVariability[] getDiscVars() {
		return DisciplineVariability.values();
	}
	
	public static EduProcGraphic getDefaultEduProcGraphicForList(
			List<EduProcGraphic> list) {
		return list.stream().sorted(new Comparator<EduProcGraphic>() {
			public int compare(EduProcGraphic o1, EduProcGraphic o2) {
				return o1.getCurriculums().size() - o2.getCurriculums().size();
			};
		}).findFirst().orElse(null);
	}
	
	public List<LocalDate> getDaysOfWeek() {
		List<LocalDate> days = new ArrayList<LocalDate>();
		for (int i = 1; i <= 6; i++) {
			days.add(LocalDate.of(2016, Month.FEBRUARY, i));
		}
		return days;
	}
}
