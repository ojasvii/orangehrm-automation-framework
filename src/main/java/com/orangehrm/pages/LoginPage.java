package com.orangehrm.pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.LogUtil;

public class LoginPage extends BasePage {

    private final By usernameField = By.name("username");
    private final By passwordField = By.name("passwor");
    private final By loginButton = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver, ExtentTest test) {
        super(driver, test);
        LogUtil.info("LoginPage initialized");
        if (test != null) {
            test.info("LoginPage initialized");
        }
    }

    public void enterUsername(String username) {
        type(usernameField, username);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void clickSubmit() {
        click(loginButton);
    }
}
