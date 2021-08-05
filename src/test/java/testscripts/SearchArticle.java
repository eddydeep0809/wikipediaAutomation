package testscripts;

import java.util.HashMap;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import resuableMethods.ExcelOperations;
import resuableMethods.ReusableMethods;

public class SearchArticle {
	
	static ReusableMethods reUsableMethods			=	new ReusableMethods();
	static ExcelOperations excelOperation			=	new ExcelOperations();
	static String projectPath 						=	System.getProperty("user.dir"); 
	static String screenshotPath 					=	projectPath+ "/Reports/Search Article/"+"Failed.png";
	static ExtentReports extentReport 				= 	new ExtentReports(projectPath + "/Reports/TestReport.html", true);
	static ExtentTest test							=	extentReport.startTest("Search Article");
	static HashMap<String, String> inputParameter	=	excelOperation.getDataForExecution("Search_Article");
	static AppiumDriver<MobileElement> driver		=	reUsableMethods.getDriver(inputParameter.get("platform"));
	
	public static void main(String args []) {
		reUsableMethods.completeOnboarding(driver, test);
		reUsableMethods.searchArticle(driver, test, inputParameter.get("title"), inputParameter.get("description"), "Search for "+inputParameter.get("title"));
		tearDown();
	}

	public static void tearDown() {
		extentReport.endTest(test);
		extentReport.flush();
		driver.quit();	
	}
}
