package com.qtpselenium.hybrid.driver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.keywords.AppKeywords;
import com.qtpselenium.hybrid.util.Constants;
import com.qtpselenium.hybrid.util.XLS_Reader;

public class DriverScript {
	public static void executeTest(String testName, AppKeywords app, Hashtable<String, String> data, XLS_Reader xls, Properties prop, Properties envProp, ExtentTest test) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Starting " + testName);
		test.log(Status.INFO, "Starting " + testName);
		app.setProp(prop);
		app.setEnvProp(envProp);
		app.setData(data);
		app.setTest(test);
		int rowCount = xls.getRowCount(Constants.SHEET_KEYWORDS);
		for (int rowNum = 2; rowNum <= rowCount; rowNum++) {
			if(testName.equals(xls.getCellData(Constants.SHEET_KEYWORDS, Constants.COL_TCID, rowNum))) {
				String keyword = xls.getCellData(Constants.SHEET_KEYWORDS, Constants.COL_KEYWORD, rowNum);
				String objectKey = xls.getCellData(Constants.SHEET_KEYWORDS, Constants.COL_OBJECT, rowNum);
				String dataKey = xls.getCellData(Constants.SHEET_KEYWORDS, Constants.COL_DATA, rowNum);
				app.setDataKey(dataKey);
				app.setObjectKey(objectKey);		
//				Retrieval
				Method method = app.getClass().getMethod(keyword);
//				Calling
				method.invoke(app);
			}
		}
	}
}
