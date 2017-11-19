package automatedTests;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pageObjects.AchievementsList;
import pageObjects.StudentFromWeb;
import university.dao.GenericDao;
import university.domain.Student;
import university.dao.StudentDaoJAXBImpl;


public class AchievementsListTest {
	
	private static GenericDao<Student> dao;
	private static List<Student> students;
	
	private static WebDriver driver = null;
	private static List <BigDecimal> weightedAveragesFromWeb;

	private static List <Integer> obtainedFirstRowGrades;
	private static HashMap<String, String> studentNameWithID;
	private static HashMap<String, StudentFromWeb> studentsFromWeb;
	
    @BeforeClass
    public static void setUp() {
		String driverPath = "C:\\Selenium\\chromedriver-233\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", driverPath);
		
		driver = new ChromeDriver();
		driver.get("http://localhost:9090/knowit/");
		
		dao = new StudentDaoJAXBImpl();
		students = dao.findAll();
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		weightedAveragesFromWeb = pageObjects.AchievementsList.weightedAverages(driver);

		obtainedFirstRowGrades = pageObjects.AchievementsList.firstRowGrades(driver);
		studentNameWithID = pageObjects.AchievementsList.studentNamesByID(driver);
		
		pageObjects.AchievementsList.findAllData(driver);
		
    }
    
 
	
    @Test
    public void weightedAverageIsSortedDescending() {

    	List <BigDecimal> sortedWeightedAverages = new ArrayList<>(weightedAveragesFromWeb);
    	
    	sortedWeightedAverages.sort((x, y) -> y.compareTo(x));
    	   	
        assertTrue(weightedAveragesFromWeb.equals(sortedWeightedAverages));
    }
	
    
    
    @Test
    public void webWeightedAverageIsCorrect() {
    	
    	String studentID = "100";
    	BigDecimal daoWeightedAverage;
    	
    	daoWeightedAverage = BigDecimal.valueOf(students.stream().filter(p -> p.getId() == Long.parseLong(studentID))
    						.map(p -> p.getWeightedAverageGrade())
    						.findFirst().orElse((double) 100));
    	
    	daoWeightedAverage = daoWeightedAverage.setScale(2, RoundingMode.HALF_UP);

    	assertTrue(AchievementsList.getStudentById(studentID).getWeightedAverage().equals(daoWeightedAverage));
    	
    }
    
    
    @Test
    public void webStudentNameMatchesToID() {
    	String studentID = "300";
    	
    	String fullName;
    	
    	fullName = students.stream().filter(p -> p.getId() == Long.parseLong(studentID))
				.map(p -> p.getFullName())
				.findFirst().orElse("Name not found!");
    	
    	assertTrue(AchievementsList.getStudentById(studentID).getFullName().equals(fullName));
    }
    
    
    @After
    public void cleanUp(){       
    	driver.manage().deleteAllCookies();
    }
    
    
    @AfterClass 
    public static void tearDown(){ 
    	driver.close(); 
    }     
    
}
