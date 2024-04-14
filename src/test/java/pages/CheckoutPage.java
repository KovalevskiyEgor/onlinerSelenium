package pages;

import io.qameta.allure.Step;
import lombok.extern.java.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import utils.Screenshoter;

@Log
public class CheckoutPage extends BasePage{
    @FindBy(xpath = "//input[@placeholder=\"Укажите населенный пункт\"]")
    private WebElement town;

    @FindBy(xpath = "(//input)[2]")
    private WebElement street;

    @FindBy(xpath = "//input[@maxlength=\"4\"]")
    private WebElement house;

    @FindBy(xpath = "(//input[@maxlength=\"2\"])[1]")
    private WebElement corpus;

    @FindBy(xpath = "(//input[@maxlength=\"2\"])[2]")
    private WebElement gate;

    @FindBy(xpath = "//input[@maxlength=\"3\"]")
    private WebElement floor;

    @FindBy(xpath = "//input[@maxlength=\"6\"]")
    private WebElement flat;

    @FindBy(xpath = "//textarea[@maxlength=\"255\"]")
    private WebElement comment;

    @FindBy(xpath = "(//input[@maxlength=\"255\"])[1]")
    private WebElement name;

    @FindBy(xpath = "(//input[@maxlength=\"255\"])[2]")
    private WebElement surname;

    @FindBy(xpath = "//input[contains(@class, \"cart-form__input_width_mmm\")]")
    private WebElement email;

    @FindBy(xpath = "//button[contains(text(),\"Перейти к способу оплаты\")]")
    private WebElement paymentButton;

    public CheckoutPage (){
        PageFactory.initElements(driver, this);
    }
    @Step("устанавливаем город")
    public void setTown(String townString) {
        town.sendKeys(townString);
    }
    @Step("устанавливаем улицу")
    public void setStreet(String streetString) {
        street.sendKeys(streetString);
    }
    @Step("устанавливаем дом")
    public void setHouse(String houseString) {
        house.sendKeys(houseString);
    }
    @Step("устанавливаем корпус")
    public void setCorpus(String corpusString) {
        corpus.sendKeys(corpusString);
    }
    @Step("устанавливаем подъезд")
    public void setGate(String gateString) {
        gate.sendKeys(gateString);
    }
    @Step("устанавливаем этаж")
    public void setFloor(String floorString) {
        floor.sendKeys(floorString);
    }
    @Step("устанавливаем квартиру")
    public void setFlat(String flatString) {
        flat.sendKeys(flatString);
    }
    @Step("устанавливаем коммент")
    public void setComment(String commentString) {
        comment.sendKeys(commentString);
    }
    @Step("устанавливаем имя")
    public void setName(String nameString) {
        name.sendKeys(nameString);
    }
    @Step("устанавливаем фамилию")
    public void setSurname(String surnameString) {
        surname.sendKeys(surnameString);
    }
    @Step("устанавливаем почту")
    public void setEmail(String emailString){
        email.sendKeys(emailString);
        Screenshoter.takeScreenshot("заполнили поля доставки");
    }
    public void goToPayment(){
        paymentButton.click();
    }
    @Step("проверка что цена правильно посчитана")
    public boolean checkIfPriceCorrect() {
        boolean isPriceCorrect;
        try {
            driver.findElement(By.xpath(String.format("//span[contains(text(),\"%s\")]",propertyReader.getProperty("laptop.price"))));
            isPriceCorrect = true;
        }catch (Exception e){
            log.info(String.format("//span[contains(text(),\"%s\")]",propertyReader.getProperty("laptop.price")));
            isPriceCorrect = false;
        }
        return isPriceCorrect;
    }
}