package pageObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AchievementsList {
	
	public static HashMap <String, StudentFromWeb> studentsFromWeb = new HashMap<>();
	

	public static StudentFromWeb getStudentById (String id) {		
		return studentsFromWeb.get(id);
	}
	
	public static List<String> fullNames(WebDriver driver) {
		
		List<WebElement> names =  driver.findElements(By.cssSelector("table>tbody>tr>td[class^='yui3-datatable-col-fullname']"));
		
		return names.stream()
				.map(p -> p.getText())
				.collect(Collectors.toList());	
	}
	
	
	public static List<BigDecimal> weightedAverages(WebDriver driver) {
		
		String decimalSeparator = String.valueOf(DecimalFormatSymbols.getInstance().getDecimalSeparator()) ;
		
		List<WebElement> weighted =  driver.findElements(By.cssSelector("table>tbody>tr>td[class^='yui3-datatable-col-weighted']"));
				
		return weighted.stream()
				.map(p -> new BigDecimal(p.getText().replace(decimalSeparator, ".")))
				.collect(Collectors.toList());	
	}
	
	
	public static List<Integer> firstRowGrades(WebDriver driver) {
			
		List<WebElement> grades =  driver.findElements(By.cssSelector("table>tbody>tr[data-yui3-record='model_1']>td[class^='yui3-datatable-col-discipline']"));
		
		return grades.stream()
				.map(p -> new Integer(p.getText()))
				.collect(Collectors.toList());	
		
	}
	
	
	public static HashMap<String, String> studentNamesByID(WebDriver driver) {
		
		List<WebElement> data =  driver.findElements(By.cssSelector("[data-yui3-record^='model_'"));
		
		Map<String, String> namesByID = new HashMap<>();
		
		namesByID = data.stream().collect(Collectors
				.toMap(id -> id.findElement(By.cssSelector("[class^='yui3-datatable-col-id']")).getText(),
						name -> name.findElement(By.cssSelector("[class^='yui3-datatable-col-fullname']")).getText()));
		
		return (HashMap<String, String>) namesByID;	
	}

	
	
	public static List<String> fullName(WebDriver driver) {
		
		List<WebElement> data =  driver.findElements(By.cssSelector("table>tbody + tbody>tr"));
		
		return data.stream()
				.map(p -> p.getText())
				.collect(Collectors.toList());	
	}
	
	
	public static Map<String, List<String>> allDataByID(WebDriver driver) {
		Map<String,  List<String>> data = new HashMap<>();
		
		List<WebElement> element =  driver.findElements(By.cssSelector("[data-yui3-record^='model_']"));
		
		element.stream().map(e -> e.findElements(By.cssSelector("[class^='yui3-datatable-col-']")).stream()
				.map(cell -> new String(cell.getText()))
				.collect(Collectors.toList())).forEach((rowCells) -> {
				data.put(rowCells.get(0), rowCells.subList(1, rowCells.size()));
				});
		
	return data;
	}
	
	
	public static HashMap <String, StudentFromWeb> studentsFromWeb(WebDriver driver) {
		HashMap<String, StudentFromWeb> data = new HashMap<>();
		String decimalSeparator = String.valueOf(DecimalFormatSymbols.getInstance().getDecimalSeparator()) ;
		
		List<WebElement> element =  driver.findElements(By.cssSelector("[data-yui3-record^='model_']"));
		
		element.stream().map(e -> e.findElements(By.cssSelector("[class^='yui3-datatable-col-']")).stream()
				.map(cell -> new String(cell.getText()))
				.collect(Collectors.toList())).forEach((rowCells) -> {
					BigDecimal wa = new BigDecimal(rowCells.get(rowCells.size()-1).replace(decimalSeparator, "."));
					data.put(rowCells.get(0), new StudentFromWeb(rowCells.get(0), rowCells.get(1), rowCells.subList(1, rowCells.size()-1), wa));
				});
		
		return data;
	}
	
	
	public static void findAllData(WebDriver driver) {
		studentsFromWeb = studentsFromWeb(driver);
		
	}
	
	
	
	
}
