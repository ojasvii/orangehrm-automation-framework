package com.orangehrm.pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.LogUtil;

public class DashboardPage extends BasePage{

    long startTime;
    long loadTime;

    private final By brandbanner = By.cssSelector(".oxd-brand-banner img");
    private final By logo = By.xpath("//img[contains(@src,'logo')]");
    private final By help = By.xpath("//button[@title='Help']");
    private final By userProfile = By.xpath("//li[contains(@class,'oxd-userdropdown')]");



    public DashboardPage(WebDriver driver, ExtentTest test) {
        super(driver, test);
        LogUtil.info("Dashboard page initialized");
    }

    public long brandBannerVerify(){
        startTime = System.currentTimeMillis();

//        wait.until(ExpectedConditions.presenceOfElementLocated(brandbanner));
        wait.until(ExpectedConditions.visibilityOfElementLocated(brandbanner));

         loadTime = System.currentTimeMillis() - startTime;

         return loadTime;
    }

    public boolean verifyLogo(){
     WebElement logoElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(logo));
     boolean isDisplayed= logoElement.isDisplayed();

     if(isDisplayed){
         test.pass("Logo is displayed");
         LogUtil.info("Logo is visible on dashboard page.");
     }else{
         test.fail("Logo is not displayed.");
         LogUtil.info("Logo is not visible on dashboard page.");
     }
     return isDisplayed;
    }

    public boolean verifyHelpIcon(){
        WebElement helpIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(help));
        boolean isDisplayedIcon = helpIcon.isDisplayed();

        if(isDisplayedIcon){
            test.pass("Help Icon is displayed");
            LogUtil.info("Helpicon is displayed");
        }else{
            test.fail("Help icon is not displayed");
            LogUtil.info("Help icon is not displayed");
        }

        return isDisplayedIcon;
    }

    public boolean verifyUserProfileIcon(){
        WebElement  userprofileIcon= wait.until(ExpectedConditions.visibilityOfElementLocated(userProfile));
        boolean userProfileIsDisplayed = userprofileIcon.isDisplayed();

        if(userProfileIsDisplayed){
            test.pass("User Profile icon is dispalyed.");
            LogUtil.info("User Profile icon is dispalyed.");
        }else{
            test.fail("User profle icon is not displayed.");
            LogUtil.info("User profle icon is not displayed.");
        }

        return userProfileIsDisplayed;
    }
}
