package com.qtpselenium.hybrid.SuiteA;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qtpselenium.hybrid.BaseTest;
import com.qtpselenium.hybrid.driver.DriverScript;
import com.qtpselenium.hybrid.util.Constants;
import com.qtpselenium.hybrid.util.DataUtil;

public class CreatePortfolioTest extends BaseTest{

	@Test(dataProvider = "getData")
	private void createPortfolioTest(Hashtable<String, String> data) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (DataUtil.isTestSkipped(testName, xls) | !data.get(Constants.COL_RUNMODE).equals(Constants.RUNMODE_YES)) {
			test.log(Status.SKIP, "RunMode is set to N");
			throw new SkipException("RunMode is set to N");
		}
		DriverScript.executeTest(testName, app, data, xls, prop, envProp, test);
	}
}