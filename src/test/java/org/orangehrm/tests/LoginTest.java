package org.orangehrm.tests;

import com.orangehrm.pages.LoginPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestDataProvider;

import java.io.IOException;
import java.util.Hashtable;


public class LoginTest extends BaseTest{


    LoginPage loginPage;


    @BeforeMethod
    public void setUpPages() {
        System.out.println("Calling the login page. ");
          loginPage  =new LoginPage(driver);
        System.out.println("Called the login page.");
    }

    //    Positive test cases
    @Test(dataProvider = "loginData")
    public void login(Hashtable<String, String> testData) throws InterruptedException {
        System.out.println("Calling hte username function");
        loginPage.enterUsername(testData.get("UserName"));
        loginPage.enterPassword(testData.get("Password"));
        loginPage.clickSubmit();

        String acutalUrl =  loginPage.getCurrentUrlAfterWait(testData.get("DashboardUrl"));
        Assert.assertEquals(acutalUrl,testData.get("DashboardUrl"),"URL verification failed.");
        driver.findElement(By.xpath("//li[@class='oxd-userdropdown']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();

    }

//    Negative test case

    @Test(dataProvider = "invalidLoginData")
    public void invalidLogin(Hashtable<String,String> testData) throws InterruptedException {
        loginPage.enterUsername(testData.get("UserName"));
        loginPage.enterPassword(testData.get("Password"));
        loginPage.clickSubmit();
        String acutalUrl =  loginPage.getCurrentUrlAfterWait(testData.get("DashboardUrl"));
        Assert.assertEquals(acutalUrl,testData.get("DashboardUrl"),"URL verification failed.");

    }


    @DataProvider(name = "loginData")
    public Object [][] loginData() throws IOException {
        return  TestDataProvider.getTestData("OrangeHRM.xlsx","login", "loginData");
    }


    @DataProvider(name = "invalidLoginData")
    public Object [][] invalidLoginData() throws IOException {
        return  TestDataProvider.getTestData("OrangeHRM.xlsx","login", "InvalidLogin");
    }

}
