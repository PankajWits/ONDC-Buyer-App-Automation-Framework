package com.automation.ui.selenium;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.automation.core.ConfigReader;
import com.automation.core.ReadEmails;

/**
 * This function defines the core selenium actions, 
 * 		@BeforeClass - Before every class, creating a new webDriver instance
 * 		@AfterClass -  Quit the WebDriver when the test is completed. 
 * 
 * All the respective webPage related classes (which contain the functions specific to that particular webPage) are also initiated here, in the setUp function
 * 
 * This class needs to be extended, for every new automation testClass
 *
 */
public class BaseTestSuite {
    protected WebDriver driver;
    protected Logger logger = LogManager.getLogger(BaseTestSuite.class.getName());
    protected static final String THREADCONTEXT_ROUTINGKEY = "RoutingKey";
    protected ReadEmails readEmail = new ReadEmails();
    
	/**
	 * Create an instance of webdriver and initiate all page
	 * 
	 * @param browser
	 * @param locale
	 * @throws Exception
	 */
	@Parameters({"browser"})
	@BeforeClass (alwaysRun = true)
	public void setUp(@Optional("Chrome") String browser) throws Exception{
		/**
		 * this sets ThreadContext map with key THREADCONTEXT_ROUTINGKEY and value from browser param.
	    this is needed despite setting ThreadContext in beforeTest and beforeMethod. When classes are run 
	    parallel, thread pool executing classes in parallel does not inherit ThreadContext set in beforeTest 
	    at test initialization. Log messages coming from other beforeClass methods such as login and 
	    feature flip needs to separated based on browser as well. 
		 */
	    ThreadContext.put(THREADCONTEXT_ROUTINGKEY, browser.toLowerCase());
	    String remoteSession = ConfigReader.getConfiguration(SeleniumConfigs.WEBDRIVER_REMOTE_PROPERTY, SeleniumConfigs.WEBDRIVER_REMOTE_DEFAULT_VALUE);
		if (remoteSession.equalsIgnoreCase("true")) {
			driver = DriverFactory.getRemoteInstance(browser);
			logger.info("WEBDRIVER IS STARTING ON REMOTE SESSION");
		} else {
			driver      = DriverFactory.getLocalInstance(browser);
			logger.info("WEBDRIVER IS STARTING ON LOCAL SESSION");
		}
	}
	
    /**
	 * Closes the current Session
	 */
	protected void quitSession() {
		driver.close();
	}
	
	/**
	 * Quit the driver, once the tests are completed
	 */
	@AfterClass	(alwaysRun = true)
	public void tearDown() {
		try {
			if (driver != null) 
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    ThreadContext.clearMap();
        }
		
	}
	
    /**
     * This is run before any xml test is run and set a thread context with key THREADCONTEXT_ROUTINGKEY
     * and value is from browser param. This is needed despite setting ThreadContext in beforeClass 
     * and beforeMethod to separate logs based on browser happening before test class initialization 
     * For e.g. some logs in TestListener and ConfigReader are run before test class initialized but we know
     * already that test class will be initialized for execution in specific browser depending on params in 
     * xml file or jvm args
     * 
     * @param browser
     */
    @BeforeTest(alwaysRun = true)
    @Parameters({ "browser" })
    public void beforeTestThreadContextSetup(@Optional("Chrome") String browser) {
        ThreadContext.put(THREADCONTEXT_ROUTINGKEY, browser.toLowerCase());
    }

    /**
     * This clears the ThreadContextMap
     */
    @AfterTest(alwaysRun = true)
    public void afterTestThreadContextCleanup() {
        ThreadContext.clearMap();
    }
    
    /**
     * This is run before any test method is run and set a thread context with key THREADCONTEXT_ROUTINGKEY
     * and value is from browser param. This is needed despite of setting ThreadContext in beforeClass and
     * beforeTest. When methods are run parallel (instead of class or tests) then the threadpool executing 
     * methods in parallel does not have/inherit the context map of parent thread. Hence each method
     * 
     * @param browser
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "browser" })
    public void beforeMethodThreadContextSetup(@Optional("Chrome") String browser) {
        ThreadContext.put(THREADCONTEXT_ROUTINGKEY, browser.toLowerCase());
    }
}
