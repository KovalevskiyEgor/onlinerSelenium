package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

public class MainPage extends BasePage{
    @FindBy(xpath = "//span[text() = \"Ноутбуки\"]")
    private WebElement laptopButton;

    public MainPage(){
        PageFactory.initElements(driver, this);
    }
    @Step("переходим в раздел ноутбуков")
    public void clickOnLaptopButton(){
        laptopButton.click();
    }
}