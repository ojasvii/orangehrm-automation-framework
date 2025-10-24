//package org.orangehrm.tests;
//
//import com.orangehrm.pages.LoginPage;
//import org.openqa.selenium.By;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Listeners;
//import org.testng.annotations.Test;
//import org.testng.asserts.SoftAssert;
//import utils.TestDataProvider;
//
//import java.io.IOException;
//import java.util.Hashtable;
//
//
//@Listeners({listeners.TestListener.class, listeners.RetryListener.class})
//public class LoginTest extends BaseTest{
//
//    SoftAssert softAssert = new SoftAssert();
//    LoginPage loginPage;
//
//
//    @BeforeMethod
//    public void setUpPages() {
//        System.out.println("Calling the login page. ");
//          loginPage  =new LoginPage(driver,test);
//        System.out.println("Called the login page.");
//    }
//
//    //    Positive test cases
//    @Test(dataProvider = "loginData")
//    public void login(Hashtable<String, String> testData) throws InterruptedException {
//        System.out.println("Calling hte username function");
//        loginPage.enterUsername(testData.get("UserName"));
//        loginPage.enterPassword(testData.get("Password"));
//        loginPage.clickSubmit();
//
//        String acutalUrl =  loginPage.getCurrentUrlAfterWait(testData.get("DashboardUrl"));
//        Assert.assertEquals(acutalUrl,testData.get("DashboardUrl"),"URL verification failed.");
//        driver.findElement(By.xpath("//li[@class='oxd-userdropdown']")).click();
//        driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
//
//    }
//
////    Negative test case
//
//    @Test(dataProvider = "invalidLoginData")
//    public void invalidLogin(Hashtable<String,String> testData) throws InterruptedException {
//        loginPage.enterUsername(testData.get("UserName"));
//        loginPage.enterPassword(testData.get("Password"));
//        loginPage.clickSubmit();
//        String acutalUrl =  loginPage.getCurrentUrlAfterWait(testData.get("DashboardUrl"));
//        Assert.assertEquals(acutalUrl,testData.get("DashboardUrl"),"URL verification failed.");
//
//    }
//
//
//    @Test
//    public void verifyLoginPageText(){
//        String actualText = loginPage.verifyUsernameLabelText("Username");
//        softAssert.assertEquals(actualText,"Username");
//
//    }
//
//
//    @DataProvider(name = "loginData")
//    public Object [][] loginData() throws IOException {
//        return  TestDataProvider.getTestData("OrangeHRM.xlsx","login", "loginData");
//    }
//
//
//    @DataProvider(name = "invalidLoginData")
//    public Object [][] invalidLoginData() throws IOException {
//        return  TestDataProvider.getTestData("OrangeHRM.xlsx","login", "InvalidLogin");
//    }
//
//}


package org.orangehrm.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;
import utils.TestDataProvider;
import com.orangehrm.pages.LoginPage;
import utils.LogUtil;
import utils.ScreenshotUtil;

import java.io.IOException;
import java.util.Hashtable;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private SoftAssert softAssert;

    @BeforeMethod
    public void setUpPages() {
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
    }

    @Test(dataProvider = "loginData")
    public void positiveLoginTest(Hashtable<String, String> testData) {
        loginPage.enterUsername(testData.get("UserName"));
        loginPage.enterPassword(testData.get("Password"));
        loginPage.clickSubmit();

        boolean urlVerified = verifyUrlWithSoftAssert(testData.get("DashboardUrl"), "Dashboard URL");
        softAssert.assertAll();

        logout();
    }

    @Test(dataProvider = "invalidLoginData")
    public void negativeLoginTest(Hashtable<String, String> testData) {
        loginPage.enterUsername(testData.get("UserName"));
        loginPage.enterPassword(testData.get("Password"));
        loginPage.clickSubmit();

        boolean urlVerified = verifyUrlWithSoftAssert(testData.get("DashboardUrl"), "Dashboard URL after invalid login");
        softAssert.assertAll();
    }

    @Test
    public void verifyUsernameLabel() {
        String actualLabel = loginPage.getUsernameLabelText();
        try {
            if (actualLabel.equals("Username")) {
                LogUtil.info("Username label verified: " + actualLabel);
                test.pass("Username label verified: " + actualLabel);
            } else {
                String screenshot = ScreenshotUtil.captureScreenshot(driver, "UsernameLabel_Failure");
                LogUtil.error("Username label verification failed: " + actualLabel);
                test.fail("Username label verification failed").addScreenCaptureFromPath(screenshot);
            }
            softAssert.assertEquals(actualLabel, "Username", "Username label mismatch");
        } catch (Exception e) {
            LogUtil.error("Exception during username label verification: " + e.getMessage());
            softAssert.fail("Exception during username label verification: " + e.getMessage());
        }
        softAssert.assertAll();
    }

    private boolean verifyUrlWithSoftAssert(String expectedUrl, String elementName) {
        boolean status = false;
        try {
            String actualUrl = driver.getCurrentUrl();
            if (actualUrl.equals(expectedUrl)) {
                LogUtil.info(elementName + " verified: " + actualUrl);
                test.pass(elementName + " verified: " + actualUrl);
                status = true;
            } else {
                String screenshot = ScreenshotUtil.captureScreenshot(driver, elementName + "_Failure");
                LogUtil.error(elementName + " verification failed. Expected: " + expectedUrl + " | Actual: " + actualUrl);
                test.fail(elementName + " verification failed").addScreenCaptureFromPath(screenshot);
            }
        } catch (Exception e) {
            LogUtil.error("Exception during " + elementName + " verification: " + e.getMessage());
            softAssert.fail("Exception during " + elementName + " verification: " + e.getMessage());
        }
        return status;
    }

    private void logout() {
        // Generic logout after login
        driver.findElement(By.xpath("//li[@class='oxd-userdropdown']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
        LogUtil.info("Logged out successfully");
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException {
        return TestDataProvider.getTestData("OrangeHRM.xlsx", "login", "loginData");
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() throws IOException {
        return TestDataProvider.getTestData("OrangeHRM.xlsx", "login", "InvalidLogin");
    }
}
