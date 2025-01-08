package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Basepage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txtEmail;

    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txtpassword;

    @FindBy(xpath = "//input[@value='Login']")
    WebElement btnLogin;

    public void setEmail(String email) {
        txtEmail.sendKeys(email);
    }

    public void setPassword(String password) {
        txtpassword.sendKeys(password);
    }

    public void clickLogin() {
        btnLogin.click();
    }
}
