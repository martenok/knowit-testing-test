package pageObjects;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class StudentFromWeb {
	
//	public static HashMap <String, StudentFromWeb> studentsFromWeb = new HashMap<>();
	
	private String fullName;
	private String id;
	private List<String> grades;
	private BigDecimal weightedAverage;
	
	StudentFromWeb (String id, String fullName, List<String> grades, BigDecimal weightedAverage) {
		this.id = id;
		this.fullName = fullName;
		this.grades = grades;
		this.weightedAverage = weightedAverage;
		
//		studentsFromWeb.put(id, this);
	}
	
//	public static StudentFromWeb getStudentById (String id) {		
//		return studentsFromWeb.get(id);
//	}
	
	public String getId () {
		return id;
	}
	
	public String getFullName () {
		return fullName;
	}
	
	public List<String> getGrades () {
		return grades;
	}

	public BigDecimal getWeightedAverage () {
		return weightedAverage;
	}
	
}
