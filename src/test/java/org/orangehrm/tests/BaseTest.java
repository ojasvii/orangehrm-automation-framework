package org.orangehrm.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.WebDriverFactory;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp(){
        String env = System.getProperty("environment");
        ConfigReader.loadConfig(env);
        driver = WebDriverFactory.createDriver(ConfigReader.getProperty("browser"));
        driver.get(ConfigReader.getProperty("url"));
        System.out.println("Url got hit from base test");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

}
