//package utils;
//
//import org.openqa.selenium.*;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.apache.commons.io.FileUtils;
//
//public class ScreenshotUtil {
//
//    public static String captureScreenshot(WebDriver driver, String screenshotName) {
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String path = System.getProperty("user.dir") + "reports/screenshots/" + screenshotName + "_" + timestamp + ".png";
//        try {
//            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            FileUtils.copyFile(src, new File(path));
//            return path;  // return screenshot path for report
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
////        return path;
//    }
//
//
//    public static String captureScreenshot(WebDriver driver, String screenshotName, WebElement elementToHighlight) {
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String path = System.getProperty("user.dir") + "/reports/screenshots/" + screenshotName + "_" + timestamp + ".png";
//
//        try {
//            // Highlight the element if provided
//            if (elementToHighlight != null) {
//                highlightElement(driver, elementToHighlight);
//            }
//
//            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            File destDir = new File(System.getProperty("user.dir") + "/reports/screenshots/");
//            if (!destDir.exists()) {
//                destDir.mkdirs();
//            }
//            FileUtils.copyFile(src, new File(path));
//            return path;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // JS method to highlight element
//    public static void highlightElement(WebDriver driver, WebElement element) {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].style.border='3px solid red'", element);
//    }
//
//}

//   ******************************************

package utils;

import org.openqa.selenium.*;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    // Screenshot with optional highlight
    public static String captureScreenshot(WebDriver driver, String screenshotName, WebElement elementToHighlight) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String path = System.getProperty("user.dir") + "/reports/screenshots/" + screenshotName + "_" + timestamp + ".png";

        try {
            if (elementToHighlight != null) highlightElement(driver, elementToHighlight);

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destDir = new File(System.getProperty("user.dir") + "/reports/screenshots/");
            if (!destDir.exists()) destDir.mkdirs();
            FileUtils.copyFile(src, new File(path));

            if (elementToHighlight != null) removeHighlight(driver, elementToHighlight);

            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Default screenshot
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        return captureScreenshot(driver, screenshotName, null);
    }

    // Highlight element
    public static void highlightElement(WebDriver driver, WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].style.border='3px solid red'; arguments[0].style.backgroundColor='yellow';",
                    element);
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Remove highlight
    public static void removeHighlight(WebDriver driver, WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border=''; arguments[0].style.backgroundColor='';", element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
