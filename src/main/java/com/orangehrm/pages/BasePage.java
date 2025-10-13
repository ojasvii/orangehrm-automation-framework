//package com.orangehrm.pages;
//
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.Status;
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import utils.LogUtil;
//
//import java.time.Duration;
//
//public class BasePage {
//
//    protected WebDriver driver;
//    protected WebDriverWait wait;
//    protected ExtentTest test;
//
//    public BasePage(WebDriver driver, ExtentTest test) {
//        this.driver = driver;
//        this.test = test;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        LogUtil.info("WebDriver initialized in BasePage");
//        if (test != null) {
//            test.log(Status.INFO, "WebDriver initialized in BasePage");
//        }
//    }
//
//    //  Highlight element before interaction
//    private WebElement highlightElement(By locator) {
//        WebElement element = driver.findElement(locator);
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//
//        try {
//            js.executeScript("arguments[0].style.border='3px solid red'", element);
//            Thread.sleep(200); // small flash
//            js.executeScript("arguments[0].style.border=''", element);
//        } catch (Exception e) {
//            LogUtil.error("Highlighting failed: " + e.getMessage());
//        }
//        return element;
//    }
//
//    //  Generic click
//    public void click(By locator) {
//        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
//        highlightElement(locator).click();
//        LogUtil.info("Clicked element: " + locator.toString());
//        if (test != null) {
//            test.log(Status.PASS, "Clicked element: " + locator.toString());
//        }
//    }
//
//    //  Generic type
//    public void type(By locator, String text) {
//        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//        highlightElement(locator).clear();
//        element.sendKeys(text);
//        LogUtil.info("Typed '" + text + "' into: " + locator.toString());
//        if (test != null) {
//            test.log(Status.PASS, "Typed '" + text + "' into: " + locator.toString());
//        }
//    }
//
//    //  Generic current url
//    public String getCurrentUrlAfterWait(String expectedUrl) {
//        wait.until(ExpectedConditions.urlToBe(expectedUrl));
//        String actualUrl = driver.getCurrentUrl();
//        LogUtil.info("Expected URL: " + expectedUrl + " | Actual URL: " + actualUrl);
//        if (test != null) {
//            test.log(Status.INFO, "Expected URL: " + expectedUrl + " | Actual URL: " + actualUrl);
//        }
//        return actualUrl;
//    }
//}

//       ****************************

package com.orangehrm.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LogUtil;
import utils.ScreenshotUtil;

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
        if (test != null) test.log(Status.INFO, "WebDriver initialized in BasePage");
    }

    // Highlight element visually
    protected WebElement highlightElement(By locator) {
        WebElement element = driver.findElement(locator);
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].style.border='3px solid red'; arguments[0].style.backgroundColor='yellow';",
                    element);
            Thread.sleep(200); // small flash for screenshot
            js.executeScript("arguments[0].style.border=''; arguments[0].style.backgroundColor='';", element);
        } catch (Exception e) {
            LogUtil.error("Highlight failed: " + e.getMessage());
        }
        return element;
    }

    // Click with highlight
    public WebElement click(By locator) {
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            highlightElement(locator); // highlight before click
            element.click();
            LogUtil.info("Clicked element: " + locator);
            if (test != null) test.log(Status.PASS, "Clicked element: " + locator);
        } catch (Exception e) {
            String screenshot = ScreenshotUtil.captureScreenshot(driver, "Click_Failure", element);
            LogUtil.error("Click failed: " + e.getMessage());
            if (test != null) test.log(Status.FAIL, "Click failed: " + e.getMessage())
                    .addScreenCaptureFromPath(screenshot);
            throw e;
        }
        return element;
    }

    // Type with highlight
    public WebElement type(By locator, String text) {
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            highlightElement(locator);
            element.clear();
            element.sendKeys(text);
            LogUtil.info("Typed '" + text + "' into: " + locator);
            if (test != null) test.log(Status.PASS, "Typed '" + text + "' into: " + locator);
        } catch (Exception e) {
            String screenshot = ScreenshotUtil.captureScreenshot(driver, "Type_Failure", element);
            LogUtil.error("Type failed: " + e.getMessage());
            if (test != null) test.log(Status.FAIL, "Type failed: " + e.getMessage())
                    .addScreenCaptureFromPath(screenshot);
            throw e;
        }
        return element;
    }

    // Verify text with highlight
    public String verifyText(By locator, String expectedText) {
        WebElement element = null;
        String actualText = "";
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            highlightElement(locator);
            actualText = element.getText().trim();
            if (!actualText.equals(expectedText)) {
                throw new Exception("Text mismatch! Expected: " + expectedText + " | Actual: " + actualText);
            }
            LogUtil.info("Text matched: " + expectedText);
            if (test != null) test.log(Status.PASS, "Text matched: " + expectedText);
        } catch (Exception e) {
            String screenshot = ScreenshotUtil.captureScreenshot(driver, "Text_Verification_Failure", element);
            LogUtil.error("Text verification failed: " + e.getMessage());
            if (test != null) test.log(Status.FAIL, "Text verification failed: " + e.getMessage())
                    .addScreenCaptureFromPath(screenshot);
            throw new RuntimeException(e);
        }
        return actualText;
    }

    // Verify logo or element visibility
    public WebElement verifyElementVisible(By locator, String elementName) {
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            highlightElement(locator);
            if (!element.isDisplayed()) throw new Exception(elementName + " not displayed");
            LogUtil.info(elementName + " is displayed");
            if (test != null) test.log(Status.PASS, elementName + " is displayed");
        } catch (Exception e) {
            String screenshot = ScreenshotUtil.captureScreenshot(driver, elementName + "_Verification_Failure", element);
            LogUtil.error(elementName + " verification failed: " + e.getMessage());
            if (test != null) test.log(Status.FAIL, elementName + " verification failed: " + e.getMessage())
                    .addScreenCaptureFromPath(screenshot);
            throw new RuntimeException(e);
        }
        return element;
    }

    // Verify current URL
    public String getCurrentUrlAfterWait(String expectedUrl) {
        String actualUrl = "";
        try {
            wait.until(ExpectedConditions.urlToBe(expectedUrl));
            actualUrl = driver.getCurrentUrl();
            LogUtil.info("Expected URL: " + expectedUrl + " | Actual URL: " + actualUrl);
            if (test != null) test.log(Status.PASS, "URL verified: " + actualUrl);
        } catch (Exception e) {
            String screenshot = ScreenshotUtil.captureScreenshot(driver, "URL_Verification_Failure");
            LogUtil.error("URL verification failed: " + e.getMessage());
            if (test != null) test.log(Status.FAIL, "URL verification failed: " + e.getMessage())
                    .addScreenCaptureFromPath(screenshot);
            throw new RuntimeException(e);
        }
        return actualUrl;
    }
}


