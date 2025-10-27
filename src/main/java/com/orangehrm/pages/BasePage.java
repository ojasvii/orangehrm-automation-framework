package com.orangehrm.pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ExtentTest test;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Highlight element visually (optional)
    protected void highlightElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].style.border='3px solid red'; arguments[0].style.backgroundColor='yellow';",
                    element);
            Thread.sleep(200);
            js.executeScript("arguments[0].style.border=''; arguments[0].style.backgroundColor='';", element);
        } catch (Exception ignored) {}
    }

    // Get text of element
    protected String getElementText(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        highlightElement(element);
        return element.getText().trim();
    }

    // Check if element is visible
    protected boolean isElementVisible(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            highlightElement(element);
            return element.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Click element
    protected void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        highlightElement(element);
        element.click();
    }

    // Type text
    protected void typeText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        highlightElement(element);
        element.clear();
        element.sendKeys(text);
    }

    public WebElement type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        highlightElement(element);
        element.clear();
        element.sendKeys(text);
        return element;
    }

    public WebElement click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        highlightElement(element);
        element.click();
        return element;
    }

    public String getText(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText().trim();
    }
}
