package resuableMethods;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import objectRepository.HomePage;
import objectRepository.OnboardingPage;
import objectRepository.SearchPage;

public class ReusableMethods {


	public void startAppiumServer() throws IOException {
		AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
		// Use any port, in case the default 4723 is already taken (maybe by another Appium server)
		serviceBuilder.usingAnyFreePort();
		// Tell serviceBuilder where node is installed. Or set this path in an environment variable named NODE_PATH
		// System.getProperty();
		serviceBuilder.usingDriverExecutable(new File("/Users/jonahss/.nvm/versions/node/v12.1.0/bin/node"));
		// Tell serviceBuilder where Appium is installed. Or set this path in an environment variable named APPIUM_PATH
		serviceBuilder.withAppiumJS(new File("/Users/jonahss/.nvm/versions/node/v12.1.0/bin/appium"));
		// The XCUITest driver requires that a path to the Carthage binary is in the PATH variable. I have this set for my shell, but the Java process does not see it. It can be inserted here.
		HashMap<String, String> environment = new HashMap<String, String>();
		environment.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));
		serviceBuilder.withEnvironment(environment);

		AppiumDriverLocalService server;
		server = AppiumDriverLocalService.buildService(serviceBuilder);
		server.start();

		String cmd = "appium";
		Runtime.getRuntime().exec(cmd);
		System.out.println("appium server started");
	}

	public AppiumDriver<MobileElement> getDriver(String platform) {

		AppiumDriver<MobileElement> driver = null;
		if(platform.equalsIgnoreCase("Android"))
		{
			DesiredCapabilities capabilities	=	new DesiredCapabilities();
			capabilities	=	DesiredCapabilities.android();       
			capabilities.setCapability("deviceName", "Emulator");       
			capabilities.setCapability("platformName", "Android");       
			capabilities.setCapability("platformVersion", "11");             
			capabilities.setCapability("automationName", "UIAutomator2");
			capabilities.setCapability("app", System.getProperty("user.dir")+"/apk/app.apk");     
			capabilities.setCapability("fullReset", "true");
			capabilities.setCapability("appWaitActivity", "*");
			try {
				driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
				Thread.sleep(4000);
			} catch (Exception e) {	
				System.out.print(e.getMessage());
			} 

		} else if(platform.equalsIgnoreCase("iOS")) {
			DesiredCapabilities capabilities	=	new DesiredCapabilities();
			capabilities	=	DesiredCapabilities.iphone();    
			capabilities.setCapability("device", "iOS");
			capabilities.setCapability("deviceName", "Emulator");       
			capabilities.setCapability("platformName", "Android");       
			capabilities.setCapability("platformVersion", "11");  
			capabilities.setCapability("takesScreenshot", true);
			capabilities.setCapability("automationName", "XCUITest");
			try {
				driver	=	new IOSDriver<MobileElement>(capabilities);
				Thread.sleep(4000);
			} catch (Exception e) {	
				System.out.print(e.getMessage());
			} 

		}
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		return driver;
	}

	public void click(AppiumDriver<MobileElement> driver, ExtentTest test, String locator, String locatorValue, String stepDescription) {
		if(locator.equalsIgnoreCase("id")) {
			driver.findElement(By .id(locatorValue)).click();
			test.log(LogStatus.PASS, stepDescription);
		} else if (locator.equalsIgnoreCase("xpath")) {
			driver.findElement(By .xpath(locatorValue)).click();
			test.log(LogStatus.PASS, stepDescription);
		}
	}

	public void inputValue(AppiumDriver<MobileElement> driver, ExtentTest test, String locator, String locatorValue, String inputValue, String stepDescription) {
		if(locator.equalsIgnoreCase("id")) {
			driver.findElement(By .id(locatorValue)).clear();
			driver.findElement(By .id(locatorValue)).sendKeys(inputValue);
			test.log(LogStatus.PASS, stepDescription);
		} else if (locator.equalsIgnoreCase("xpath")) {
			driver.findElement(By .xpath(locatorValue)).clear();
			driver.findElement(By .xpath(locatorValue)).sendKeys(inputValue);
			test.log(LogStatus.PASS, stepDescription);
		}
	}

	public void goBack(AppiumDriver<MobileElement> driver, ExtentTest test, String stepDescription) {
		try{
			Thread.sleep(700);
			driver.navigate().back();
			test.log(LogStatus.PASS, stepDescription);
		} catch( Exception e) {

		}
	}
	
	public void acceptAlert(AppiumDriver<MobileElement> driver, ExtentTest test, String stepDescription) {
		Alert alert	=	driver.switchTo().alert();
		alert.accept();
		test.log(LogStatus.PASS, stepDescription);
	}
	
	public void setValue(AppiumDriver<MobileElement> driver, ExtentTest test, String locator, String locatorValue, String inputValue, String stepDescription) {
		if(locator.equalsIgnoreCase("id")) {
			driver.findElement(By .id(locatorValue)).setValue(inputValue);
			test.log(LogStatus.PASS, stepDescription);
		} else if (locator.equalsIgnoreCase("xpath")) {
			driver.findElement(By .xpath(locatorValue)).setValue(inputValue);
			test.log(LogStatus.PASS, stepDescription);

		}
	}
	public String getText(AppiumDriver<MobileElement> driver, ExtentTest test, String locator, String locatorValue, String stepDescription) {
		String textValue="";
		try {
			Thread.sleep(3000);

			if(locator.equalsIgnoreCase("id")) {
				textValue	=	driver.findElement(By .id(locatorValue)).getText();
				test.log(LogStatus.PASS, stepDescription);
			}  else if (locator.equalsIgnoreCase("xpath")) {
				textValue	=	driver.findElement(By .xpath(locatorValue)).getText();
				test.log(LogStatus.PASS, stepDescription);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			test.log(LogStatus.FAIL, "Failed: "+stepDescription);
		}

		return textValue;
	}

	public static void getScreenshot(AppiumDriver<MobileElement> driver, String outputlocation ) {
		try {
			File srcFiler=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFiler, new File(outputlocation));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void completeOnboarding(AppiumDriver<MobileElement> driver, ExtentTest test)
	{
		click(driver, test, "id", OnboardingPage.continue_btn, "Clicked on Continue button on Onboarding page");
		int screenNumber = 1;
		boolean onboardingFlowCompleted	=	false;
		while(!onboardingFlowCompleted) {
			try {
				click(driver, test, "id", OnboardingPage.getStarted_btn, "Onboarding flow completed");
				onboardingFlowCompleted	= true;
			} catch(Exception ElementNotFound) {
				click(driver, test, "id", OnboardingPage.continue_btn, "Proceeding with Onboarding screen "+screenNumber);
			}
			screenNumber++;
		}
	}
	public void searchArticle (AppiumDriver<MobileElement> driver, ExtentTest test, String searchTitle, String description, String stepDescription) {
		click(driver, test, "xpath", HomePage.searchBtn, "Click on Search button");
		click(driver, test, "xpath", SearchPage.search_btn, "Click on input box");
		inputValue(driver, test, "xpath", SearchPage.search_input, searchTitle, "Entered title to be searched");
		//checking if the search results table is displayed in order to iterate through it & find the matching value
		if(driver.findElement(By .xpath(SearchPage.searchResults_table)).isDisplayed())
		{
			MobileElement searchTable =	driver.findElement(By .xpath(SearchPage.searchResults_table));
			//Capturing title of all the search results in a List
			List <MobileElement> searchResultsTitle	=	searchTable.findElements(By .xpath(SearchPage.searchResults_title_txt));
			boolean titleMatched	=	false, descriptionMatched	=	false;
			//Iterating through the List containing Title in order to match the title
			for(int searchResultNumber = 0; searchResultNumber < searchResultsTitle.size(); searchResultNumber++) {
				if(searchResultsTitle.get(searchResultNumber).getText().equalsIgnoreCase(searchTitle)) {
					titleMatched	=	true;
					//Capturing description of all the search results in a List
					List <MobileElement> searchResultsDescription	=	searchTable.findElements(By .xpath(SearchPage.searchResults_description_txt));
					//Iterating through the list to match the description
					for(int searchResultsDescriptionNo = 0; searchResultsDescriptionNo < searchResultsDescription.size(); searchResultsDescriptionNo++) {
						if(searchResultsDescription.get(searchResultsDescriptionNo).getText().equalsIgnoreCase(description)) {
							searchResultsDescription.get(searchResultsDescriptionNo).click();
							descriptionMatched 	= true;
							break;
						}
					}
					if(!descriptionMatched) {
						System.out.println("Description not matched");
					} else {
						break;
					}
				}
			}
			if(!titleMatched) {
				System.out.println("title not found");
			}
		} else {
			System.out.println("No search results");
		}

	}

}
