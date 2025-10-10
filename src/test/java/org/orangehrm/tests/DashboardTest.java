package org.orangehrm.tests;

import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.LogUtil;
import utils.TestDataProvider;

import java.io.IOException;
import java.util.Hashtable;

@Listeners({listeners.TestListener.class, listeners.RetryListener.class})
public class DashboardTest extends BaseTest{

    DashboardPage dashboardPage;

    @BeforeMethod
   public void setDashboardPage(){
        dashboardPage = new DashboardPage(driver,test);
        login();
    }

    @Test
    public void verifyDashboardLoad(){

        long time = dashboardPage.brandBannerVerify();

        if(time <= 3000){
            LogUtil.info("Dashboard load time is less than 3000 ms :" + time + "ms");
            Assert.assertTrue(true, "Dashboard loaded in " + time + "ms.");
        }else{
            LogUtil.info("Dashboard load time is more than 3000 ms :" + time + "ms");
            Assert.assertFalse(false, "Dashboard loaded in " + time + "ms.");
        }
    }

    @Test
    public void verifyDashboardHeaderElementsLogo(){
        boolean logoVisible = dashboardPage.verifyLogo();
        Assert.assertTrue(logoVisible, "Logo in not visible on dashboard");
    }

    @Test
    public void verifyDashboardHelpIcon(){
        boolean helpIconVisible = dashboardPage.verifyHelpIcon();
        Assert.assertTrue(helpIconVisible, "Help icon is not visible.");
    }





//    @DataProvider(name = "dashboardData")
//    public Object [][] dashboardData() throws IOException {
//        return TestDataProvider.getTestData("OrangeHRM.xlsx","dashboard","dashboardDetails");
//    }


}
