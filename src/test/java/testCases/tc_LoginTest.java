package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.Homepage;
import pageObjects.LoginPage;
import pageObjects.MyAccountsPage;
import testBase.BaseClass;

public class tc_LoginTest extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void verifyLogin() {
        logger.info("***tc_LoginTest***");
        try {
            Homepage hp = new Homepage(driver);
            hp.clickMyAccount();
            hp.clickLogin();

            LoginPage lp = new LoginPage(driver);
            lp.setEmail(prop.getProperty("email"));
            lp.setPassword(prop.getProperty("password"));
            lp.clickLogin();

            MyAccountsPage mp = new MyAccountsPage(driver);
            Boolean mpExist = mp.isMyAccountPageExists();
//        Assert.assertEquals(mpExist, true, "Login failed");
            Assert.assertTrue(mpExist);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
