package pages;

import org.openqa.selenium.WebDriver;
import utils.PropertyReader;

public abstract class BasePage {
    protected static WebDriver driver;
    public static PropertyReader propertyReader= new PropertyReader("config.properties");

    public static void setUp(WebDriver webDriver){
        driver=webDriver;
    }
}