package com.orangehrm.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LogUtil;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ExtentTest test;

    public BasePage(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        LogUtil.info("WebDriver initialized in BasePage");
        if (test != null) {
            test.log(Status.INFO, "WebDriver initialized in BasePage");
        }
    }

    // ✅ Highlight element before interaction
    private WebElement highlightElement(By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            js.executeScript("arguments[0].style.border='3px solid red'", element);
            Thread.sleep(200); // small flash
            js.executeScript("arguments[0].style.border=''", element);
        } catch (Exception e) {
            LogUtil.error("Highlighting failed: " + e.getMessage());
        }
        return element;
    }

    // ✅ Generic click
    public void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        highlightElement(locator).click();
        LogUtil.info("Clicked element: " + locator.toString());
        if (test != null) {
            test.log(Status.PASS, "Clicked element: " + locator.toString());
        }
    }

    // ✅ Generic type
    public void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        highlightElement(locator).clear();
        element.sendKeys(text);
        LogUtil.info("Typed '" + text + "' into: " + locator.toString());
        if (test != null) {
            test.log(Status.PASS, "Typed '" + text + "' into: " + locator.toString());
        }
    }

    // ✅ Generic current url
    public String getCurrentUrlAfterWait(String expectedUrl) {
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String actualUrl = driver.getCurrentUrl();
        LogUtil.info("Expected URL: " + expectedUrl + " | Actual URL: " + actualUrl);
        if (test != null) {
            test.log(Status.INFO, "Expected URL: " + expectedUrl + " | Actual URL: " + actualUrl);
        }
        return actualUrl;
    }
}
