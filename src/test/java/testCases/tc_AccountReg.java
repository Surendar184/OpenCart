package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountsRegistrationPage;
import pageObjects.Homepage;
import testBase.BaseClass;

public class tc_AccountReg extends BaseClass {

    @Test(groups = {"Sanity", "Master"})
    public void verify_account_registration() {

        logger.info("******TC started*******");
        try {
            Homepage hp = new Homepage(driver);
            hp.clickMyAccount();
            logger.info("******clicked MyAccount*******");
            hp.clickRegister();
            logger.info("******clicked Register*******");

            AccountsRegistrationPage regpage = new AccountsRegistrationPage(driver);
            regpage.setFirstName(randomString() + "random");
            regpage.setLastName(randomString() + "lass");
            regpage.setEmail(randomString() + "asd@123.com");
            regpage.setTel("98658864");
            regpage.setPassword("asdf123");
            regpage.setConfirmPass("asdf123");
            regpage.clickPrivacyPolicy();
            regpage.clickContinue();

            logger.info("******Validating expected msgr*******");
            String cmsg = regpage.getConfirmationMsg();
            System.out.println("****" + cmsg);
            Assert.assertEquals(cmsg, "Your Account Has Been Created!");
        } catch (Exception e) {
            logger.error("Test failed...");
            logger.error("Debug logs...");
            Assert.fail();
        }
    }


}
