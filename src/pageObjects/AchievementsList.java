package pageObjects;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AchievementsList {
	
	public static HashMap <String, StudentFromWeb> studentsFromWeb = new LinkedHashMap<>();
	

	public static StudentFromWeb getStudentById (String id) {		
		return studentsFromWeb.get(id);
	}
	
	
	public static HashMap <String, StudentFromWeb> studentsFromWeb(WebDriver driver) {

		String decimalSeparator = String.valueOf(DecimalFormatSymbols.getInstance().getDecimalSeparator()) ;
		
		HashMap<String, StudentFromWeb> data = new LinkedHashMap<>();
		
		List<WebElement> element =  driver.findElements(By.cssSelector("[data-yui3-record^='model_']"));
		
		element.stream().map(e -> e.findElements(By.cssSelector("[class^='yui3-datatable-col-']"))
				.stream().map(cell -> new String(cell.getText()))
				.collect(Collectors.toList()))
				.forEach((rowCells) -> {
					BigDecimal wa = new BigDecimal(rowCells.get(rowCells.size()-1).replace(decimalSeparator, "."));
					data.put(rowCells.get(0), new StudentFromWeb(rowCells.get(0), rowCells.get(1), rowCells.subList(1, rowCells.size()-1), wa));
				});

		return data;
	}
	
	
	public static void findAllData(WebDriver driver) {
		studentsFromWeb = studentsFromWeb(driver);
		
	}
	
	
	
	
}
