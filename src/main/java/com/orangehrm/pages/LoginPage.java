package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{


    private By usernamefield = By.name("username");
    private By passwordfield = By.name("password");
    private By loginbutton = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver){

        super(driver);
        System.out.println("Called the base page.");
    }

    public void enterUsername(String username){
        System.out.println("username in loginpage");
        type(usernamefield,username);
        System.out.println("0===================");
    }

    public void enterPassword(String password){
        type(passwordfield,password);
    }

    public void clickSubmit(){
        click(loginbutton);
    }


}
