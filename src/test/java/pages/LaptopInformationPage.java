package pages;

import io.qameta.allure.Step;
import lombok.extern.java.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import utils.Screenshoter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Log
public class LaptopInformationPage extends BasePage{
    public static JavascriptExecutor js = (JavascriptExecutor) driver;
    @FindBy(xpath = "//span[contains(text(),\"Все ясно, спасибо\")]")
    private WebElement acceptNotification;

    @FindBy(xpath = "//h1")
    private WebElement laptopModel;

    public LaptopInformationPage (){
        PageFactory.initElements(driver, this);
    }
    private String getLowestPrice(List<String> list){
        Collections.sort(list, (s1, s2) -> s1.compareToIgnoreCase(s2));
        Collections.sort(list, Comparator.comparingInt(String::length).thenComparing(String::compareTo));
        log.info(String.format("//div[contains(@class,\"offers-list__description_nodecor\" ) and contains(text(),\"%s\")]",list.get(0)));
        return (String.format("//div[contains(@class,\"offers-list__description_nodecor\" ) and contains(text(),\"%s\")]",list.get(0)));
    }
    @Step("добавляем ноутбук в корзину")
    public void addLaptopToBasket(){
        List<String> listString = new ArrayList<>();
        try {
            WebElement moreOffersButton = driver.findElement(By.xpath("//span[contains(text(),\"Показать еще\")]"));
            moreOffersButton.click();
        }catch (NoSuchElementException e){}

        List<WebElement> priceList = driver.findElements(By.xpath("//div[contains(@class,\"offers-list__description_nodecor\" ) and contains(text(),\"р.\")]"));
        priceList.forEach(element -> listString.add(element.getText()));
        String laptopWithLowestPriceXPath = getLowestPrice(listString);
        WebElement laptopWithLowestPrice = driver.findElement(By.xpath(laptopWithLowestPriceXPath));
        js.executeScript("arguments[0].scrollIntoView(true);", laptopWithLowestPrice);
        if(acceptNotification.isDisplayed()) acceptNotification.click();

        WebElement basketButton = driver.findElement(By.xpath("("+laptopWithLowestPriceXPath+"//ancestor::div[@class=\"offers-list__flex\"]//a[contains(text(),\" В корзину\")])[2]"));
        basketButton.click();
        Screenshoter.takeScreenshot("добавить в корзину");
    }
    @Step("переходим в корзину")
    public void goToBasket(){
        try {
            WebElement goToBasketButton = driver.findElement(By.xpath("//a[contains(text(),\"Перейти в корзину\")]"));
            goToBasketButton.click();
        }catch (NoSuchElementException e){
            WebElement goToBasketButton = driver.findElement(By.xpath("(//a[contains(text(),\"В корзине\")])[2]"));
            goToBasketButton.click();
        }
        Screenshoter.takeScreenshot("перейти в корзину");
    }
    @Step("проверка тот ли выбран ноутбук")
    public boolean checkIfItsRightLaptop() {
        String laptopModelString = propertyReader.getProperty("laptop.model").trim().toLowerCase();
        return laptopModel.getText().trim().toLowerCase().contains(laptopModelString);
    }
}