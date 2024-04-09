package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.BasePage;
import java.time.Duration;

public abstract class BaseTest {
    protected ChromeDriver driver;

    @BeforeMethod
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(BasePage.propertyReader.getLongProperty("timeout.implicitly.wait")));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(BasePage.propertyReader.getLongProperty("timeout.page.load")));
        driver.get(BasePage.propertyReader.getProperty("base.URL"));
        BasePage.setUp(driver);
    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}