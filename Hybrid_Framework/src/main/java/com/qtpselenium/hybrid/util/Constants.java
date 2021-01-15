package com.qtpselenium.hybrid.util;

public class Constants {
	public static final String PROJECT_ROOT_DIRECTORY = System.getProperty("user.dir") + "/";
	public static final String RESOURCES_DIRECTORY = PROJECT_ROOT_DIRECTORY + "src/test/resources/";
	public static final String PROJECT_PROPERTIES_FILE = RESOURCES_DIRECTORY + "project.properties";
	public static final String REPORTS_DIRECTORY = PROJECT_ROOT_DIRECTORY + "Reports" + "/";
	
	public static final String SHEET_TESTCASES = "TestCases";
	public static final String SHEET_DATA = "Data";
	public static final String SHEET_KEYWORDS = "Keywords";
	
	public static final String COL_TCID = "TCID";
	public static final String COL_RUNMODE = "RunMode";
	public static final String COL_KEYWORD = "Keyword";
	public static final String COL_OBJECT = "Object";
	public static final String COL_DATA = "Data";
	
	public static final String RUNMODE_YES = "Y";
	
	public static final String GRID_PROPERTY_VAR = "grid";
	public static final String GRID_YES = "y";

	public static final String BROWSER_CHROME = "Chrome";
	public static final String BROWSER_FIREFOX = "Firefox";
	public static final String BROWSER_IE = "IE";
}
