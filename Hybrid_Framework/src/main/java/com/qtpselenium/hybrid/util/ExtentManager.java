package com.qtpselenium.hybrid.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	public static String reportsFolder;
	public static String screenshotsFolder;
	
	public static ExtentReports extent;
	
	public static ExtentReports getInstance(String reportsRootPath) {
		if (extent == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_z_HH_mm_ss");
			reportsFolder = reportsRootPath + sdf.format(new Date()) + "/";
			screenshotsFolder = reportsFolder + "Screenshots/";
			new File(screenshotsFolder).mkdirs();
			createInstance(reportsFolder + "index.html");
		}
		return extent;
	}

	private static void createInstance(String htmlReportFilePath) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(htmlReportFilePath);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setDocumentTitle("Test Automation Report");
		htmlReporter.config().setReportName("Execution Reports");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	}

}
