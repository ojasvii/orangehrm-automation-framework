package com.orangehrm.pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.LogUtil;

public class DashboardPage extends BasePage{

    long startTime;
    long loadTime;

    private final By brandbanner = By.cssSelector(".oxd-brand-banner img");


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

}
