package org.orangehrm.tests;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.orangehrm.pages.DashboardPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.LogUtil;
import utils.ScreenshotUtil;

public class DashboardTest extends BaseTest {

    private DashboardPage dashboardPage;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void setUpDashboard() {
        dashboardPage = new DashboardPage(driver);
        login(); // login() is in BaseTest
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

        verifyElementWithSoftAssert(dashboardPage.isLogoVisible(), "Logo", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isHelpIconVisible(), "Help Icon", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isUserProfileVisible(), "User Profile Icon", softAssert);

        softAssert.assertAll(); // Collate all header failures
    }

    @Test
    public void verifyLeftNavigationMenu() {

        verifyElementWithSoftAssert(dashboardPage.isAdminTabVisible(), "Admin Tab", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isPimTabVisible(), "PIM Tab", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isLeaveTabVisible(), "Leave Tab", softAssert);

        softAssert.assertAll(); // Collate all menu tab failures
    }

    @Test
    public void verifyDashboardTitle() {

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

    @Test
    public void verifyBrowserTabTitle() throws InterruptedException {

        try {
            String actualTitle = driver.getTitle();
            String expectedTitle = "OrangeHRM";
            Thread.sleep(3000);


            if (actualTitle.equals(expectedTitle)) {
                LogUtil.info("Browser tab title is matched:" + actualTitle);
                test.log(Status.PASS, "Browser tab title is matched:" + actualTitle);
            } else {
                String base64 = ScreenshotUtil.captureScreenshot(driver, "DashboardTitle_Failure");
                LogUtil.error("Browser tab title mismatch. Expected: " + expectedTitle + " | Actual: " + actualTitle);
                test.fail("Browser tab title mismatch. Expected: " + expectedTitle + " | Actual: " + actualTitle,
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Title Mismatch Screenshot").build());
                Assert.fail("Browser tab title mismatch. Expected: " + expectedTitle + " | Actual: " + actualTitle);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void verifyDashboardWidgets() {

        verifyElementWithSoftAssert(dashboardPage.isTimeAtWorkVisible(), "Time At Work", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isMyActionVisible(), "My Action", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isQuickLaunchVisible(), "Quick Launch", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isBuzzLatestPostsVisible(), "Buzz Latest", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isEmployeeLeaveTodayVisible(), "Employee leave Today", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isEmployeeDistributionUnitVisible(), "Employee Distribution Unit", softAssert);
        verifyElementWithSoftAssert(dashboardPage.isEmployeeDistributionByLocationVisible(), "Employee Location", softAssert);

        softAssert.assertAll();
    }


    @Test
    public void verifyMenuNavi(){
        //admin
       dashboardPage.click(By.xpath("//span[normalize-space()='Admin']"));
        String adminHeaderText = dashboardPage.getText(By.xpath("//span[@class='oxd-topbar-header-breadcrumb']"));
        verifyMenuNavigation("Admin User Management","admin/view",adminHeaderText,softAssert);

//        pim
        dashboardPage.click(By.xpath("//span[normalize-space()='PIM']"));
        String pimHeaderTextActual = dashboardPage.getText(By.xpath("//span[@class='oxd-topbar-header-breadcrumb']//h6"));
        verifyMenuNavigation("PIM","pim",pimHeaderTextActual,softAssert);

//      leave
        dashboardPage.click(By.xpath("//span[normalize-space()='Leave']"));
        String leaveHeaderTextActual = dashboardPage.getText(By.xpath("//span[@class='oxd-topbar-header-breadcrumb']//h6"));
        verifyMenuNavigation("Leave","leave/viewLeaveList",leaveHeaderTextActual,softAssert);


//      time
        dashboardPage.click(By.xpath("//span[normalize-space()='Time']"));
        String timeHeaderTextActual = dashboardPage.getText(By.xpath("//span[@class='oxd-topbar-header-breadcrumb']//h6"));
        verifyMenuNavigation("Time Timesheets","time/viewEmployeeTimesheet",timeHeaderTextActual,softAssert);

//      recuritment
        dashboardPage.click(By.xpath("//span[normalize-space()='Recruitment']"));
        String recruitmentHeaderTextActual = dashboardPage.getText(By.xpath("//span[@class='oxd-topbar-header-breadcrumb']//h6"));
        verifyMenuNavigation("Recruitment","recruitment/viewCandidates",recruitmentHeaderTextActual,softAssert);

        softAssert.assertAll();
    }



//    **************************************



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
                test.fail("Exception while verifying : " + elementName,
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Title Mismatch Screenshot").build());
                softAssert.fail("Exception while verifying : " + elementName);

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

    private void verifyMenuNavigation(String expectedHeaderText, String expectedUrl, String actualText, SoftAssert softAssert) {
        String actualUrl = driver.getCurrentUrl();
        String pageHeaderText = actualText;

        boolean urlMatched = actualUrl.toLowerCase().contains(expectedUrl.toLowerCase());
        boolean headerMatched = pageHeaderText.equalsIgnoreCase(expectedHeaderText);

        if (headerMatched) {
            LogUtil.info("Header Matched successfully");
            test.log(Status.PASS, expectedHeaderText + "is matched successfully.");
        } else {
            String base64 = ScreenshotUtil.captureScreenshot(driver, expectedHeaderText);
            LogUtil.error("Header Mismatch: expected - " + expectedHeaderText + "but found" + pageHeaderText);
            test.fail("Exception while verifying text: Expected - " + expectedHeaderText + " Actual Header text -" + pageHeaderText,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Header Mismatch Screenshot").build());
            softAssert.fail("Exception while verifying text: Expected - " + expectedHeaderText + " Actual Header text -" + pageHeaderText);
        }

        if (urlMatched) {
            LogUtil.info("Url Matched successfully");
            test.log(Status.PASS, expectedUrl + "is matched successfully.");
        } else {
            String base64 = ScreenshotUtil.captureScreenshot(driver, expectedUrl);
            LogUtil.error("Url Mismatch: expected - " + expectedUrl + "but found" + actualUrl);
            test.fail("Exception while verifying url : Expected - " + expectedUrl + " Actual -" + actualUrl,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64, "Url Mismatch Screenshot").build());
            softAssert.fail("Exception while verifying url : Expected - " + expectedUrl + " Actual -" + actualUrl);
        }

    }
}