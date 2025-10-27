package org.orangehrm.tests;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.orangehrm.pages.DashboardPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.LogUtil;
import utils.ScreenshotUtil;

public class DashboardTest extends BaseTest {

    private DashboardPage dashboardPage;

    @BeforeMethod
    public void setUpDashboard() {
        dashboardPage = new DashboardPage(driver);
        login(); // assuming login() is in BaseTest
    }

    @Test
    public void verifyDashboardLoadTime() {
        long loadTime = dashboardPage.getBrandBannerLoadTime();
        try {
            if (loadTime <= 3000) {
                LogUtil.info("Dashboard loaded in: " + loadTime + " ms");
                test.log(Status.PASS, "Dashboard loaded in: " + loadTime + " ms");
            } else {
                String screenshot = ScreenshotUtil.captureScreenshot(driver, "DashboardLoadTime_Failure");
                LogUtil.error("Dashboard load time exceeded 3000 ms: " + loadTime);
                test.log(Status.FAIL, "Dashboard load time exceeded 3000 ms").addScreenCaptureFromPath(screenshot);
            }
        } catch (Exception e) {
            LogUtil.error("Exception during dashboard load time check: " + e.getMessage());
            throw e;
        }
    }

    @Test
    public void verifyDashboardHeaderElements() {
        SoftAssert softAssert = new SoftAssert();

        verifyElementWithSoftAssert(dashboardPage.isLogoVisible(), "Logo", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isHelpIconVisible(), "Help Icon", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isUserProfileVisible(), "User Profile Icon", softAssert);

        softAssert.assertAll(); // Collate all header failures
    }

    @Test
    public void verifyLeftNavigationMenu() {
        SoftAssert softAssert = new SoftAssert();

        verifyElementWithSoftAssert(dashboardPage.isAdminTabVisible(), "Admin Tab", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isPimTabVisible(), "PIM Tab", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isLeaveTabVisible(), "Leave Tab", softAssert);

        softAssert.assertAll(); // Collate all menu tab failures
    }

    @Test
    public void verifyDashboardTitle() {
        SoftAssert softAssert = new SoftAssert();
        String expectedTitle = "Dashboard";
        try {
            String actualTitle = dashboardPage.getDashboardTitleText();
            if (actualTitle.equals(expectedTitle)) {
                LogUtil.info("Dashboard title verified: " + actualTitle);
                test.log(Status.PASS, "Dashboard title verified: " + actualTitle);
            } else {
                String base64 = ScreenshotUtil.captureScreenshot(driver, "DashboardTitle_Failure");
                LogUtil.error("Dashboard title mismatch. Expected: " + expectedTitle + " | Actual: " + actualTitle);
                test.fail("Dashboard title mismatch. Expected: " + expectedTitle + " | Actual: " + actualTitle,
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Title Mismatch Screenshot").build());
                softAssert.fail("Dashboard title mismatch. Expected: " + expectedTitle + " | Actual: " + actualTitle);
            }
        } catch (Exception e) {
            String base64 = ScreenshotUtil.captureScreenshot(driver, "DashboardTitle_Exception");
            LogUtil.error("Exception while verifying dashboard title: " + e.getMessage());
            test.fail("Exception while verifying dashboard title: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Exception Screenshot").build());
            softAssert.fail("Exception while verifying dashboard title: " + e.getMessage());
        }

        softAssert.assertAll();
    }



    /**
     * Reusable method for checking element visibility using SoftAssert
     */
    private void verifyElementWithSoftAssert(boolean isVisible, String elementName, SoftAssert softAssert) {
        try {
            if (isVisible) {
                LogUtil.info(elementName + " is visible.");
                test.log(Status.PASS, elementName + " is visible.");
            } else {
//                String screenshot = ScreenshotUtil.captureScreenshot(driver, elementName + "_Failure");
//                LogUtil.error(elementName + " is not visible.");
//                test.log(Status.FAIL, elementName + " is not visible.")
//                        .addScreenCaptureFromPath(screenshot);


                String base64 = ScreenshotUtil.captureScreenshot(driver, elementName + "_Failure");
                LogUtil.error(elementName + " is not visible.");
                test.fail("Exception while verifying : " +elementName,
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Title Mismatch Screenshot").build());
                softAssert.fail("Exception while verifying : " +elementName);

            }
            softAssert.assertTrue(isVisible, elementName + " is not visible.");
        } catch (Exception e) {
//            LogUtil.error("Exception while verifying " + elementName + ": " + e.getMessage());
//            softAssert.fail("Exception while verifying " + elementName + ": " + e.getMessage());

            String base64 = ScreenshotUtil.captureScreenshot(driver, elementName);
            LogUtil.error("Exception while verifying : " + e.getMessage());
            test.fail("Exception while verifying : " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Exception Screenshot").build());
            softAssert.fail("Exception while verifying : " + e.getMessage());
        }
    }
}
