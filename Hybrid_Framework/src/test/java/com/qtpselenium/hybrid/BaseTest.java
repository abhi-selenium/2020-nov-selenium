package com.qtpselenium.hybrid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.qtpselenium.hybrid.keywords.AppKeywords;
import com.qtpselenium.hybrid.util.Constants;
import com.qtpselenium.hybrid.util.DataUtil;
import com.qtpselenium.hybrid.util.ExtentManager;
import com.qtpselenium.hybrid.util.XLS_Reader;

public class BaseTest {
	public Properties prop = null; // project.properties
	public Properties envProp = null; // either prod or uat
	public XLS_Reader xls = null;
	public String testName;
	public String suiteName;
	public ExtentReports report;
	public ExtentTest test;
	public AppKeywords app = null;
	
	@BeforeTest
	public void beforeTest() throws FileNotFoundException, IOException {
		prop = new Properties();
		prop.load(new FileInputStream(Constants.PROJECT_PROPERTIES_FILE));
		envProp = new Properties();
		envProp.load(new FileInputStream(Constants.RESOURCES_DIRECTORY + prop.getProperty("env") +".properties"));
		testName = this.getClass().getSimpleName();
		suiteName = this.getClass().getName().split("\\.")[this.getClass().getName().split("\\.").length-2];
		xls = new XLS_Reader(Constants.RESOURCES_DIRECTORY + suiteName + ".xlsx");
	}
	
	@BeforeMethod
	public void beforeMethod() {
		app = new AppKeywords();
		report = ExtentManager.getInstance(Constants.REPORTS_DIRECTORY);
		test = report.createTest(testName);
	}
	
	@AfterMethod
	public void afterMethod() {
		if (report != null) {
			if (test != null) {
				report.flush();
			}
		}
		if (app.driver != null) {
			app.driver.quit();
		}
	}
	
	@DataProvider
	public Object[][] getData() {
		return DataUtil.getTestData(testName, xls);
	}
}
