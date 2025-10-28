//package com.orangehrm.pages;
//
//import com.aventstack.extentreports.ExtentTest;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import utils.LogUtil;
//
//public class DashboardPage extends BasePage{
//
//    long startTime;
//    long loadTime;
//
//    private final By brandbanner = By.cssSelector(".oxd-brand-banner img");
//    private final By logo = By.xpath("//img[contains(@src,'log')]");
//    private final By help = By.xpath("//button[@title='Help']");
//    private final By userProfile = By.xpath("//li[contains(@class,'oxd-userdropdown')]");
//    private final By adminTab = By.xpath("//span[text()='Admin']");
//    private final By pimTab = By.xpath("//span[text()='PIM']");
//    private final By leaveTab = By.xpath("//span[normalize-space()='Leave']");
//    private final By dashboardTitle = By.xpath("//div[@class='oxd-topbar-header-title']//h6");
//
//
//
//    public DashboardPage(WebDriver driver, ExtentTest test) {
//        super(driver, test);
//        LogUtil.info("Dashboard page initialized");
//    }
//
//    public long brandBannerVerify(){
//        startTime = System.currentTimeMillis();
//
////        wait.until(ExpectedConditions.presenceOfElementLocated(brandbanner));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(brandbanner));
//
//         loadTime = System.currentTimeMillis() - startTime;
//
//         return loadTime;
//    }
//
//    public boolean verifyLogo(){
//     WebElement logoElement =  wait.until(ExpectedConditions.visibilityOfElementLocated(logo));
//     boolean isDisplayed= logoElement.isDisplayed();
//
//     if(isDisplayed){
//         test.pass("Logo is displayed");
//         LogUtil.info("Logo is visible on dashboard page.");
//     }else{
//         test.fail("Logo is not displayed.");
//         LogUtil.info("Logo is not visible on dashboard page.");
//     }
//     return isDisplayed;
//    }
//
//    public boolean verifyHelpIcon(){
//        WebElement helpIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(help));
//        boolean isDisplayedIcon = helpIcon.isDisplayed();
//
//        if(isDisplayedIcon){
//            test.pass("Help Icon is displayed");
//            LogUtil.info("Helpicon is displayed");
//        }else{
//            test.fail("Help icon is not displayed");
//            LogUtil.info("Help icon is not displayed");
//        }
//
//        return isDisplayedIcon;
//    }
//
//    public boolean verifyUserProfileIcon(){
//        WebElement  userprofileIcon= wait.until(ExpectedConditions.visibilityOfElementLocated(userProfile));
//        boolean userProfileIsDisplayed = userprofileIcon.isDisplayed();
//
//        if(userProfileIsDisplayed){
//            test.pass("User Profile icon is dispalyed.");
//            LogUtil.info("User Profile icon is dispalyed.");
//        }else{
//            test.fail("User profle icon is not displayed.");
//            LogUtil.info("User profle icon is not displayed.");
//        }
//
//        return userProfileIsDisplayed;
//    }
//
//
//    public boolean verifyAdminTab(){
//        WebElement admin = wait.until(ExpectedConditions.visibilityOfElementLocated(adminTab));
//        boolean adminTabDisplayed = admin.isDisplayed();
//        if(adminTabDisplayed){
//            test.pass("Admin tab is displayed");
//            LogUtil.info("Admin tab is displayed");
//        }else{
//            test.fail("Admin tab is not displayed");
//            LogUtil.info("Admin tab is not displayed");
//        }
//        return adminTabDisplayed;
//    }
//
//    public boolean verifyPimTab(){
//        WebElement pim = wait.until(ExpectedConditions.visibilityOfElementLocated(pimTab));
//        boolean pimTabDisplayed = pim.isDisplayed();
//
//        if(pimTabDisplayed){
//            test.pass("Pim tab is displayed.");
//            LogUtil.info("Pim tab is not displayed.");
//        }else{
//            test.fail("Pim tab is not displayed.");
//            LogUtil.info("Pim tab is not displayed.");
//        }
//
//        return pimTabDisplayed;
//    }
//
//    public boolean verifyLeaveTab(){
//       WebElement leave =  wait.until(ExpectedConditions.visibilityOfElementLocated(leaveTab));
//       boolean leaveTabDisplayed = leave.isDisplayed();
//
//       if(leaveTabDisplayed){
//           test.pass("Leave tab is displayed.");
//           LogUtil.info("Leave Tab is displayed.");
//       }else{
//           test.fail("Leave tab is not displayed.");
//           LogUtil.info("Leave tab is not displayed.");
//       }
//       return leaveTabDisplayed;
//    }
//
//
//    public String verifyDashboardTitle(String expectedTitle){
//        String actualTitlePageDashboard = getPageTitle(dashboardTitle,expectedTitle);
//        return actualTitlePageDashboard;
//    }
//}

package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    // Locators
    private final By brandBanner = By.cssSelector(".oxd-brand-banner img");
    private final By logo = By.xpath("//img[contains(@src,'log')]");
    private final By help = By.xpath("//button[@title='Help']");
    private final By userProfile = By.xpath("//li[contains(@class,'oxd-userdropdown')]");
    private final By adminTab = By.xpath("//span[text()='Admin']");
    private final By pimTab = By.xpath("//span[normalize-space()='PIM']");
    private final By leaveTab = By.xpath("//span[normalize-space()='Leave']");
    private final By dashboardTitle = By.xpath("//div[@class='oxd-topbar-header-title']//h6");
    private final By timeAtWork = By.xpath("//p[normalize-space()='Time at Work']");
    private final By myAction = By.xpath("//p[normalize-space()='My Actions']");
    private final By quickLaunch = By.xpath("//p[normalize-space()='Quick Launch']");
    private final By buzzLatestPosts = By.xpath("//p[normalize-space()='Buzz Latest Posts']");
    private final By employeesLeaveToday = By.xpath("//p[normalize-space()='Employees on Leave Today']");
    private final By employeeDistributionUnit = By.xpath("//p[normalize-space()='Employee Distribution by Sub Unit']");
    private final By employeeByLocation = By.xpath("//p[normalize-space()='Employee Distribution by Location']");




    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // Dashboard heading
    public String getDashboardTitleText() {
        return getElementText(dashboardTitle);
    }

    // Brand banner load time
    public long getBrandBannerLoadTime() {
        long start = System.currentTimeMillis();
        isElementVisible(brandBanner);
        return System.currentTimeMillis() - start;
    }

    // Verify elements visibility
    public boolean isLogoVisible() {
        return isElementVisible(logo);
    }

    public boolean isHelpIconVisible() {
        return isElementVisible(help);
    }

    public boolean isUserProfileVisible() {
        return isElementVisible(userProfile);
    }

    public boolean isAdminTabVisible() {
        return isElementVisible(adminTab);
    }

    public boolean isPimTabVisible() {
        return isElementVisible(pimTab);
    }

    public boolean isLeaveTabVisible() {
        return isElementVisible(leaveTab);
    }

    public boolean isTimeAtWorkVisible(){
        return isElementVisible(timeAtWork);
    }

    public boolean isMyActionVisible(){
        return isElementVisible(myAction);
    }

    public boolean isQuickLaunchVisible(){
        return isElementVisible(quickLaunch);
    }

    public boolean isEmployeeLeaveTodayVisible(){
        return isElementVisible(employeesLeaveToday);
    }

    public boolean isBuzzLatestPostsVisible(){
        return isElementVisible(buzzLatestPosts);
    }

    public boolean isEmployeeDistributionUnitVisible(){
        return isElementVisible(employeeDistributionUnit);
    }

    public boolean isEmployeeDistributionByLocationVisible(){
        return isElementVisible(employeeByLocation) ;
    }


}
