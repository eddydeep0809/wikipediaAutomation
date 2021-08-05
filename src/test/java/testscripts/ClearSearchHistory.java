package testscripts;

import java.util.HashMap;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import objectRepository.SearchPage;
import resuableMethods.ExcelOperations;
import resuableMethods.ReusableMethods;

public class ClearSearchHistory {

	static ReusableMethods reUsableMethods			=	new ReusableMethods();
	static ExcelOperations excelOperation			=	new ExcelOperations();
	static String projectPath 						=	System.getProperty("user.dir"); 
	static String screenshotPath 					=	projectPath+ "/Reports/Clear Search History/"+"Failed.png";
	static ExtentReports extentReport 				= 	new ExtentReports(projectPath + "/Reports/TestReport.html", true);
	static ExtentTest test							=	extentReport.startTest("Clear Search History");
	static HashMap<String, String> inputParameter	=	excelOperation.getDataForExecution("Clear_Search_History");
	static AppiumDriver<MobileElement> driver		=	reUsableMethods.getDriver(inputParameter.get("platform"));


	public static void main(String args []) {
		reUsableMethods.completeOnboarding(driver, test);
		reUsableMethods.searchArticle(driver, test, inputParameter.get("title"), inputParameter.get("description"), "Search for "+inputParameter.get("title"));
		for(int clickBackButton = 1; clickBackButton <= 3; clickBackButton++) {
			reUsableMethods.goBack(driver, test, "Clicked on back button");
		}
		//Click on Delete history button
		reUsableMethods.click(driver, test, "id", SearchPage.searhHistoryDelete_btn, "Click on Delete history button");
		//Confirm the pop-up
		reUsableMethods.acceptAlert(driver, test, "Click Yes on delete history popup");
		try {
			reUsableMethods.click(driver, test, "id", SearchPage.searhHistoryDelete_btn, "Click on Delete history button");
			ReusableMethods.getScreenshot(driver, screenshotPath);
			test.log(LogStatus.FAIL, "Search history not deleted"+test.addScreenCapture(screenshotPath));

		} catch(Exception ElementNotFound) {
			test.log(LogStatus.PASS, "Search history deleted successfully");
		}
		tearDown();
	}

	public static void tearDown() {
		extentReport.endTest(test);
		extentReport.flush();
		driver.quit();	
	}
}
