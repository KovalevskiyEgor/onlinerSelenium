package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

public class BasketPage extends BasePage{
    @FindBy(xpath = "//a[contains(text(),\"Перейти к оформлению\")]")
    private WebElement checkOutButton;

    public BasketPage() {
        PageFactory.initElements(driver, this);
    }
    @Step("переход на страницу оплаты")
    public void goToCheckout(){
        checkOutButton.click();
    }
}