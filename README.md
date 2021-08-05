# wikipediaAutomation
It is an automation framework which can be used to execute tests on the wikpedia app (https://github.com/wikimedia/apps-android-wikipedia)

Pre-requisite:
Either emulator or android device should be connected with usb debugging enabled and appium must be running.

Project setup
1) import the Git project into IDE after importing the repository
2) update the table for test data in the testData.xlsx folder corresponding the test case to be executed like the platform(Android or iOS), title(title to be searched), 
and description(description of the title to be searched), if needed.
3) run the java class as Java application for the test case to be executed (either ClearSearchHistory.java or SearchArticle.java)
4) The result of the test execution can be seen by accessing the TestReport.html file present in the Reports folder
5) Assertions are added as part of test pass or fail status that is logged in the test report
