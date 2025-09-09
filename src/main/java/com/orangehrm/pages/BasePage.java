package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

// this page is for common functions.

public class BasePage {


    protected WebDriver driver;
    protected WebDriverWait wait;

    BasePage(WebDriver driver){
        this.driver =driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Webdriver driver initialized");
    }

    //Generic click
    public void click(By locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // Generic type
    public void type(By locator, String text){
        WebElement element  = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    // generic current url
    public String getCurrentUrlAfterWait(String expectedUrl) {
        // Wait until URL matches
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String actualUrl = driver.getCurrentUrl();
        System.out.println("Actual URL: " + actualUrl);
        return actualUrl;   // return instead of assert
    }



}
