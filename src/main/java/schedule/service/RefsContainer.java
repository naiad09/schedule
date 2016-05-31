package schedule.service;

import org.springframework.stereotype.Service;

import schedule.domain.persons.Lecturer.Degree;
import schedule.domain.persons.LecturerJob.JobType;
import schedule.domain.schedule.CommonDiscipline.DisciplineVariability;
import schedule.domain.struct.Chair.Faculty;
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
}