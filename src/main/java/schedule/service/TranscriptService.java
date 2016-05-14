package schedule.service;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import schedule.domain.Chair;
import schedule.domain.LecturerJob;
import schedule.domain.persons.Lecturer;


@Service
public class TranscriptService {
	
	private XPath xPath = XPathFactory.newInstance().newXPath();
	private Document doc;
	
	public TranscriptService() throws Exception {
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		f.setValidating(false);
		DocumentBuilder builder = f.newDocumentBuilder();
		
		File file = new File(getClass().getClassLoader()
				.getResource("transcript.xml").getFile());
		
		doc = builder.parse(file);
		
		initTranscript();
	}
	
	private void initTranscript() throws Exception {
		// TODO here and in transcript.xml
		for (LecturerJob.JobType f : LecturerJob.JobType.values()) {
			f.transcript.setShortName(xPath
					.compile("//jobs/job[@id='" + f.name() + "']/@shortName")
					.evaluate(doc));
			f.transcript.setFullName(xPath
					.compile("//jobs/job[@id='" + f.name() + "']/@fullName")
					.evaluate(doc));
		}
		
		for (Chair.Faculty f : Chair.Faculty.values()) {
			f.transcript.setFullName(xPath.compile(
					"//faculties/faculty[@id='" + f.name() + "']/@fullName")
					.evaluate(doc));
			f.transcript.setShortName(xPath.compile(
					"//faculties/faculty[@id='" + f.name() + "']/@shortName")
					.evaluate(doc));
		}
		
		for (Lecturer.Degree f : Lecturer.Degree.values()) {
			f.transcript.setFullName(xPath.compile(
					"//degrees/degree[@id='" + f.name() + "']/@fullName")
					.evaluate(doc));
			f.transcript.setShortName(xPath.compile(
					"//degrees/degree[@id='" + f.name() + "']/@shortName")
					.evaluate(doc));
		}
	}
}
