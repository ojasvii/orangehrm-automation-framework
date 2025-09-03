package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{


    private By usernamefield = By.name("username");
    private By passwordfield = By.name("password");
    private By loginbutton = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public void enterUsername(String username){
        type(usernamefield,username);
    }

    public void enterPassword(String password){
        type(passwordfield,password);
    }

    public void clickSubmit(){
        click(loginbutton);
    }


}
