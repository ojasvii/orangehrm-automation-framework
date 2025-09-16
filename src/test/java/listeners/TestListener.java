package listeners;

import com.aventstack.extentreports.Status;
import utils.ExtentTestManager;
import utils.LogUtil;
import utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.*;

public class TestListener implements ITestListener {

    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static void setDriver(WebDriver driver) {
        driverThread.set(driver);
    }

    public static WebDriver getDriver() {
        return driverThread.get();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = getDriver();
        if (driver != null) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());
            LogUtil.error("Test Failed: " + result.getMethod().getMethodName() + " | Screenshot: " + screenshotPath, result.getThrowable());

            // Extent Report
            ExtentTestManager.getTest().log(Status.FAIL, "Screenshot below: " +
                    ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath));
        }
    }
}
