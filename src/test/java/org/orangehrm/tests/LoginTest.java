package org.orangehrm.tests;

import com.orangehrm.pages.LoginPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class LoginTest extends BaseTest{


    LoginPage loginPage;


    @BeforeMethod
    public void setUpPages() {
        System.out.println("Calling the login page. ");
          loginPage  =new LoginPage(driver);
        System.out.println("Called the login page.");
    }

    //    Positive test cases
    @Test
    public void login() throws InterruptedException {
        System.out.println("Calling hte username function");
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickSubmit();

        String acutalUrl =  loginPage.getCurrentUrlAfterWait("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
        Assert.assertEquals(acutalUrl,"https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index","URL verification failed.");
        driver.findElement(By.xpath("//li[@class='oxd-userdropdown']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();

    }

//    Negative test case

    @Test
    public void invalidLogin() throws InterruptedException {
        loginPage.clickSubmit();
    }



}
