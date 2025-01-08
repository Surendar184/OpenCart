package testBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {
    public static WebDriver driver;
    public Logger logger;
    public Properties prop;


    @BeforeClass(groups = {"Sanity", "Regression", "Master"})
    @Parameters({"os", "browser"})

    public void setup(String os, String br) throws IOException {

        FileReader file = new FileReader("src/test/resources/Config.properties");
        prop = new Properties();
        prop.load(file);

        logger = LogManager.getLogger(this.getClass());

        if (prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities caps = new DesiredCapabilities();
            if (os.equalsIgnoreCase("windows")) {
                caps.setPlatform(Platform.WIN11);
            } else {
                System.out.println("no os");
                return;
            }

            switch (br.toLowerCase()) {
                case "chrome":
                    caps.setBrowserName("chrome");
                    break;
                case "edge":
                    caps.setBrowserName("MicrosoftEdge");
                    break;
                default:
                    System.out.println("match not found");
                    return;
            }
//            driver = new RemoteWebDriver(new URL("http://192.168.31.210:4444/ui/"), caps);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps);
        }

        if (prop.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                default:
                    System.out.println("invalid browser name");
                    return;
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

//        driver.get("https://tutorialsninja.com/demo/index.php?route=account/register");
        driver.get(prop.getProperty("url"));
        driver.manage().window().maximize();
//        WebDriverWait wait = new WebDriverWait(driver, 20);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Register']")));
    }

    @AfterClass(groups = {"Sanity", "Regression", "Master"})
    public void tearDown() {
        driver.quit();
    }

    public String randomString() {
        String rstring = RandomStringUtils.randomAlphabetic(5);
        return rstring;
    }

    public String captureScreenshot(String tname) {
        String timestamp = new SimpleDateFormat("yyyymmddhhss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timestamp + ".png";
        File targetFile = new File(targetFilePath);

        srcFile.renameTo(targetFile);
        return targetFilePath;
    }
}
