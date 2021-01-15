package com.qtpselenium.hybrid.keywords;

import com.aventstack.extentreports.Status;

public class AppKeywords extends GenericKeywords{
	public void validateLogin() throws Exception {
		System.out.println("Validating Login");
		test.log(Status.INFO, "Validating Login");
		if (isElementPresent(objectKey)) {
			if (data.get(dataKey).equals("Success")) {
				reportFailure("Expected Success but encountered Failure");
			}
		} else {
			if (data.get(dataKey).equals("Failure")) {
				reportFailure("Expected Failure but encountered Success");
			}
		}
		System.out.println("Validation Complete");
		test.log(Status.INFO, "Validation Complete");
//		if (getObject().getText().equals(data.get(dataKey))) {
//			System.out.println(getObject().getText());
//		}
	}
}
