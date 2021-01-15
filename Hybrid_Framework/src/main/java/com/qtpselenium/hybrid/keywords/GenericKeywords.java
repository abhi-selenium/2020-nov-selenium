package com.qtpselenium.hybrid.keywords;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.util.Constants;
import com.qtpselenium.hybrid.util.ExtentManager;
import com.sun.org.apache.bcel.internal.Const;

public class GenericKeywords {
	public Properties prop = null;
	public Properties envProp = null;
	public Hashtable<String, String> data;

	public String objectKey;
	public String dataKey;
	
	public ExtentTest test;
	
	public WebDriver driver;
	public void openBrowser() throws Exception {
		System.out.println("Opening " + data.get(dataKey) + " Browser");
		test.log(Status.INFO, "Opening " + data.get(dataKey) + " Browser");
		if (prop.getProperty(Constants.GRID_PROPERTY_VAR).equals(Constants.GRID_YES)) {
			URL remoteAddress = new URL("http://192.168.1.16:4444/wd/hub");
			if (data.get(dataKey).equals(Constants.BROWSER_CHROME)) {
				ChromeOptions capabilities = new ChromeOptions();
				capabilities.addArguments("--start-maximized");
				driver = new RemoteWebDriver(remoteAddress, capabilities);
			} else if (data.get(dataKey).equals(Constants.BROWSER_FIREFOX)) {
				FirefoxOptions capabilities = new FirefoxOptions();
				driver = new RemoteWebDriver(remoteAddress, capabilities);
			}
		} else {
			if (data.get(dataKey).equals(Constants.BROWSER_CHROME)) {
				driver = new ChromeDriver();
				driver.manage().window().maximize();
			} else if (data.get(dataKey).equals(Constants.BROWSER_FIREFOX)) {
				driver = new FirefoxDriver();
			} else if (data.get(dataKey).equals(Constants.BROWSER_IE)) {
				driver = new InternetExplorerDriver();
			}
		}
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	public void navigate() {
		System.out.println("Navigating to " + envProp.getProperty(objectKey));
		test.log(Status.INFO, "Navigating to " + envProp.getProperty(objectKey));
		driver.get(envProp.getProperty(objectKey));
	}
	
	public void click() throws Exception{
		System.out.println("Clicking on '" + prop.getProperty(objectKey) + "'");
		test.log(Status.INFO, "Clicking on '" + prop.getProperty(objectKey) + "'");
		try {
			getObject(objectKey).click();
		} catch (Exception e) {
			reportFailure(e.getMessage());
		}
	}

	public void type() throws Exception {
		System.out.println("Typing '" + data.get(dataKey) +  "' inside '" + prop.getProperty(objectKey) + "'");
		test.log(Status.INFO, "Typing '" + data.get(dataKey) +  "' inside '" + prop.getProperty(objectKey) + "'");
		try {
			getObject(objectKey).sendKeys(data.get(dataKey));
		} catch (Exception e) {
			reportFailure(e.getMessage());
		}
	}
	

//	UTILITY METHODS

	public WebElement getObject(String objectKey) throws Exception {
		WebElement element = null;
		try {
			if (objectKey.endsWith("_css")) {
				element = driver.findElement(By.cssSelector(prop.getProperty(objectKey)));
			} else if (objectKey.endsWith("_xpath")) {
				element = driver.findElement(By.xpath(prop.getProperty(objectKey)));
			} else if (objectKey.endsWith("_id")) {
				element = driver.findElement(By.id(prop.getProperty(objectKey)));
			} else if (objectKey.endsWith("_name")) {
				element = driver.findElement(By.name(prop.getProperty(objectKey)));
			}
		} catch (Exception e) {
			reportFailure(e.getMessage());
		}
		return element;
	}

	public boolean isElementPresent(String objectKey) {
		List<WebElement> elements = null;
		if (objectKey.endsWith("_css")) {
			elements = driver.findElements(By.cssSelector(prop.getProperty(objectKey)));
		} else if (objectKey.endsWith("_xpath")) {
			elements = driver.findElements(By.xpath(prop.getProperty(objectKey)));
		} else if (objectKey.endsWith("_id")) {
			elements = driver.findElements(By.id(prop.getProperty(objectKey)));
		} else if (objectKey.endsWith("_name")) {
			elements = driver.findElements(By.name(prop.getProperty(objectKey)));
		}
		if (elements.size()>0) {
			return true;
		}
		return false;
	}

	public void reportFailure(String message) throws Exception {
		takeScreenshot();
		test.log(Status.FAIL, message);
		Assert.fail(message);
	}
	
	public void takeScreenshot() throws Exception {
//		Take Screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
//		Save Screenshot
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_z_HH_mm_ss");
		String screenshotFileName = sdf.format(new Date()) + ".png";
		FileUtils.copyFile(screenshot, new File(ExtentManager.screenshotsFolder + screenshotFileName));
		test.log(Status.INFO, "Screenshot Here" + test.addScreenCaptureFromPath(ExtentManager.screenshotsFolder + screenshotFileName));
		
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public void setEnvProp(Properties envProp) {
		this.envProp = envProp;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public void setData(Hashtable<String, String> data) {
		this.data = data;
	}

	public void setTest(ExtentTest test) {
		this.test = test;
	}
}
