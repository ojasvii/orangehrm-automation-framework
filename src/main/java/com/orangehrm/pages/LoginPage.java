package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;

    private By usernamefield = By.name("username");
    private By passwordfield = By.name("password");
    private By loginbutton = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void enterUsername(String username){
        driver.findElement(usernamefield).sendKeys(username);
    }

    public void enterPassword(String password){
        driver.findElement(passwordfield).sendKeys(password);
    }

    public void clickSubmit(){
        driver.findElement(loginbutton).click();
    }
}
