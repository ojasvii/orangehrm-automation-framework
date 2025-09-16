package org.orangehrm.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebElement;
import utils.ConfigReader;
import utils.WebDriverFactory;
import utils.ScreenshotUtil;
import utils.LogUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    @BeforeSuite(alwaysRun = true)
    public void setupReport() {
        // Jenkins + Local friendly report path
        String reportDir = System.getProperty("user.dir") + File.separator + "reports";
        new File(reportDir).mkdirs();

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = reportDir + File.separator + "ExecutionReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setReportName("OrangeHRM Automation Report");
        spark.config().setDocumentTitle("Automation Test Results");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        LogUtil.info("Extent Report initialized: " + reportPath);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        String env = System.getProperty("environment", "qa"); // default to QA
        ConfigReader.loadConfig(env);

        driver = WebDriverFactory.createDriver(ConfigReader.getProperty("browser"));
        driver.get(ConfigReader.getProperty("url"));

        // attach driver to TestNG context (useful for listeners)
        org.testng.Reporter.getCurrentTestResult().getTestContext().setAttribute("driver", driver);

        LogUtil.info("Browser launched: " + ConfigReader.getProperty("browser"));
        LogUtil.info("URL launched: " + ConfigReader.getProperty("url"));

        // Create test node for Extent
        test = extent.createTest(method.getName())
                .assignCategory(this.getClass().getSimpleName());
        test.info("Starting test: " + method.getName());
    }

//    @AfterMethod(alwaysRun = true)
//    public void tearDown(ITestResult result) {
//        try {
//            if (result.getStatus() == ITestResult.FAILURE) {
//                String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
//                test.fail("Test Failed: " + (result.getThrowable() != null ? result.getThrowable() : "Unknown error"));
//                if (screenshotPath != null) {
//                    test.addScreenCaptureFromPath(screenshotPath);
//                }
//                LogUtil.error("FAILED: " + result.getName(), result.getThrowable());
//            } else if (result.getStatus() == ITestResult.SUCCESS) {
//                test.pass("Test Passed");
//                LogUtil.info("PASSED: " + result.getName());
//            } else if (result.getStatus() == ITestResult.SKIP) {
//                test.skip("Test Skipped: " + (result.getThrowable() != null ? result.getThrowable() : ""));
//                LogUtil.warn("SKIPPED: " + result.getName());
//            }
//        } catch (Exception e) {
//            LogUtil.error("Error in tearDown reporting", e);
//        } finally {
//            if (driver != null) {
//                driver.quit();
//                LogUtil.info("Browser closed for test: " + result.getName());
//            }
//        }
//    }

//    @AfterMethod(alwaysRun = true)
//    public void tearDown(ITestResult result) {
//        String screenshotPath = null;
//
//        WebElement failedElement = null;
//
//        try {
//            // Capture screenshot for FAILURE or SKIP or any throwable
//            if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SKIP
//                    || result.getThrowable() != null) {
//                screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
//            }
//
//            // FAILURE
//            if (result.getStatus() == ITestResult.FAILURE) {
//                test.fail("Test Failed: " + (result.getThrowable() != null ? result.getThrowable() : "Unknown error"));
//                if (screenshotPath != null) {
//                    test.addScreenCaptureFromPath(screenshotPath);
//                }
//                LogUtil.error("FAILED: " + result.getName(), result.getThrowable());
//
//                // SUCCESS
//            } else if (result.getStatus() == ITestResult.SUCCESS) {
//                test.pass("Test Passed");
//                LogUtil.info("PASSED: " + result.getName());
//
//                // SKIPPED
//            } else if (result.getStatus() == ITestResult.SKIP) {
//                test.skip("Test Skipped: " + (result.getThrowable() != null ? result.getThrowable() : "Skipped without exception"));
//                if (screenshotPath != null) {
//                    test.addScreenCaptureFromPath(screenshotPath);
//                }
//                LogUtil.warn("SKIPPED: " + result.getName());
//
//                // Any other unexpected case
//            } else {
//                test.warning("Test status unknown: " + result.getName());
//                if (screenshotPath != null) {
//                    test.addScreenCaptureFromPath(screenshotPath);
//                }
//                LogUtil.warn("UNKNOWN STATUS: " + result.getName());
//            }
//
//        } catch (Exception e) {
//            LogUtil.error("Error in tearDown reporting", e);
//        } finally {
//            if (driver != null) {
//                driver.quit();
//                LogUtil.info("Browser closed for test: " + result.getName());
//            }
//        }
//    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        String screenshotPath = null;
        WebElement failedElement = null;

        try {
            // Check if the test class has a 'failedElement' field (optional)
            Object testClassInstance = result.getInstance();
            try {
                failedElement = (WebElement) testClassInstance.getClass()
                        .getDeclaredField("failedElement").get(testClassInstance);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // No failedElement declared in the test – ignore
                failedElement = null;
            }

            // Capture screenshot with optional highlight
            screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName(), failedElement);

            // FAILURE
            if (result.getStatus() == ITestResult.FAILURE) {
                test.fail("Test Failed: " + (result.getThrowable() != null ? result.getThrowable() : "Unknown error"));
                if (screenshotPath != null) {
                    test.addScreenCaptureFromPath(screenshotPath);
                }
                LogUtil.error("FAILED: " + result.getName(), result.getThrowable());

                // SUCCESS
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                test.pass("Test Passed");
                LogUtil.info("PASSED: " + result.getName());

                // SKIPPED
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.skip("Test Skipped: " + (result.getThrowable() != null ? result.getThrowable() : "Skipped without exception"));
                if (screenshotPath != null) {
                    test.addScreenCaptureFromPath(screenshotPath);
                }
                LogUtil.warn("SKIPPED: " + result.getName());

                // UNKNOWN
            } else {
                test.warning("Test status unknown: " + result.getName());
                if (screenshotPath != null) {
                    test.addScreenCaptureFromPath(screenshotPath);
                }
                LogUtil.warn("UNKNOWN STATUS: " + result.getName());
            }

        } catch (Exception e) {
            LogUtil.error("Error in tearDown reporting", e);
        } finally {
            if (driver != null) {
                driver.quit();
                LogUtil.info("Browser closed for test: " + result.getName());
            }
        }
    }


    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        if (extent != null) {
            extent.flush();
            LogUtil.info("Extent Report flushed successfully");
        }
    }
}
