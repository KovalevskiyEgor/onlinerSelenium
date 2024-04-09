package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

public class BasketPage extends BasePage{
    @FindBy(xpath = "//a[contains(text(),\"Перейти к оформлению\")]")
    private WebElement checkOutButton;

    public BasketPage() {
        PageFactory.initElements(driver, this);
    }
    public void goToCheckout(){
        checkOutButton.click();
    }
}
