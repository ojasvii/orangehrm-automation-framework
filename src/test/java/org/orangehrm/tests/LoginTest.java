package org.orangehrm.tests;

import com.orangehrm.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest{


    LoginPage loginPage;

    @BeforeMethod
    public void setUpPages() {
          loginPage  =new LoginPage(driver);
    }
    //    Positive test cases
    @Test
    public void login() throws InterruptedException {
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickSubmit();

        Thread.sleep(5000);
        String currentUrl = driver.getCurrentUrl();
        System.out.println("dashboard url : " + currentUrl );
        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
        Assert.assertEquals(currentUrl, expectedUrl);

        driver.findElement(By.xpath("//li[@class='oxd-userdropdown']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();

    }

//    Negative test case

    @Test
    public void invalidLogin() throws InterruptedException {
        Thread.sleep(5000);
        loginPage.clickSubmit();
    }



}
