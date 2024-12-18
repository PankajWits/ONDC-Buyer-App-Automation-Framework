package com.automation.ui.selenium;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import com.automation.core.RetryFailedTests;

public class TestListener extends TestListenerAdapter implements IAnnotationTransformer {
    public Logger logger = LogManager.getLogger();
    private static final String LOG_4J_Path = "/resources/config/log4j2.xml";

    public void onTestStart(ITestResult result) {
        logger.info("############## STARTING TEST " + result.getName() + " ####################");
    }

    static {
        System.setProperty("log4j.configurationFile", System.getProperty("user.dir") + LOG_4J_Path);
    }

    /**
     * Update the test case browser result to PASS. Updates test case final
     * result to FAIL if any browser has status fail If no browser has FAIL
     * status then mark final status as PASS. Serialize suiteResult to JSON
     * 
     * @param result
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        removeSkippedTest(result);
    }

    /**
     * Run Failed Test cases again
     */
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        IRetryAnalyzer retry = annotation.getRetryAnalyzer();
        if (retry == null) {
            annotation.setRetryAnalyzer(RetryFailedTests.class);
        }
    }

    /**
     * When a test case fails and retried, the it is marked as SKIP. SKIP test
     * case also appears in failed-testng.xml and jenkins retries it regardless
     * of result in retry attempt This function clears all such SKIP entries.
     * 
     * @param iResult
     */
    public void removeSkippedTest(ITestResult iResult) {
        ITestContext context = iResult.getTestContext();
        Iterator<ITestResult> skippedTC = context.getSkippedTests().getAllResults().iterator();
        while (skippedTC.hasNext()) {
            ITestResult skipped = skippedTC.next();
            ITestNGMethod skippedMethod = skipped.getMethod();
            if (context.getSkippedTests().getResults(skippedMethod).size() > 0) {
                logger.info("Removing skipped entry for test case: " + skipped.getName());
                skippedTC.remove();
            }
        }
    }

}
