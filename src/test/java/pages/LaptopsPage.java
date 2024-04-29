package pages;

import io.qameta.allure.Step;
import lombok.extern.java.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.w3c.dom.*;
import utils.Screenshoter;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

@Log
public class LaptopsPage extends BasePage{
    private static String CHECK_BOX_XPATH = "//div[@class=\"catalog-form__checkbox-sign\" and contains(text(),\"%s\")]";
    public JavascriptExecutor js = (JavascriptExecutor) driver;
    @FindBy(xpath = "(//div[contains(@class,'input-style__real')])[1]")
    WebElement producersListButton;

    @FindBy(xpath = "//input[@placeholder=\"2008\"]")
    WebElement marketLaunchDateFrom;

    @FindBy(xpath = "//span[contains(text(),\"Показать\")]")
    WebElement allFiltersButton;

    @FindBy(xpath = "//input[@placeholder=\"2024\"]")
    WebElement marketLaunchDateTo;
    WebElement matrixCheckBox;
    WebElement materialCheckBox;
    WebElement numpadCheckBox;
    List<WebElement> laptopsList;

    public LaptopsPage(){
        PageFactory.initElements(driver, this);
    }
    @Step("Выбираем производителей")
    public void setProducers(){
        scrollToElementAndClick(producersListButton);
        setProducers(readXmlWithProducers());
        Screenshoter.takeScreenshot("производители");
    }
    @Step("устанавливаем дату выхода на рынок")
    public void setMarketLaunchDate(String from, String to){
        marketLaunchDateFrom.sendKeys(from);
        marketLaunchDateTo.sendKeys(to);
        Screenshoter.takeScreenshot("дата выхода на рынок");
    }
    @Step("устанавливаем матрицу")
    public void setMatrix(String matrixName){
        matrixCheckBox = driver.findElement(By.xpath(String.format(CHECK_BOX_XPATH,matrixName)));
        scrollToElementAndClick(matrixCheckBox);
        Screenshoter.takeScreenshot("матрица");
    }
    @Step("устанавливаем материал")
    public void setMaterial(String metalName){
        scrollToElementAndClick(allFiltersButton);
        materialCheckBox = driver.findElement(By.xpath(String.format(CHECK_BOX_XPATH, metalName)));
        scrollToElementAndClick(materialCheckBox);
        Screenshoter.takeScreenshot("материал");
    }
    @Step("устанавливаем numpad")
    public void setNumpad(String isNumpadNeeded){
        String value = isNumpadNeeded.equals("Да")?"1":"0";
        numpadCheckBox = driver.findElement(By.xpath(String.format("//input[@name=\"numpad\" and @value=\"%s\"]/..",value)));
        numpadCheckBox.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Screenshoter.takeScreenshot("numpad");
    }
    @Step("выбор среднего ноутбука")
    public void clickOnAverageLaptop(){
        laptopsList = driver.findElements(By.xpath("//div[contains(@class,\"catalog-form__description_huge-additional\")]//span[contains(text(),\"р.\")and not(@class)]"));
        scrollToElementAndClick(getAverageLaptop(laptopsList));
        Screenshoter.takeScreenshot("средний ноут");
    }
    @Step("проверка выбраны ли производители")
    public boolean checkIfProducersSelected(){
        ArrayList<String> producersList = readXmlWithProducers();
        boolean isSelected = false;
        for(String producer: producersList){
            WebElement checkbox = driver.findElement(By.xpath(producer+"//../..//input"));
            isSelected = checkbox.isSelected();
        }
        return isSelected;
    }
    @Step("проверка выбрана ли дата выхода на рынок")
    public boolean checkIfMarketDateSet(String marketLaunchDateFromString, String marketLaunchDateToString) {
        return (marketLaunchDateFrom.getAttribute("value").equals(marketLaunchDateFromString) &&
                marketLaunchDateTo.getAttribute("value").equals(marketLaunchDateToString));
    }
    private WebElement getAverageLaptop(List<WebElement> laptopsList) {
        List<String> listString = new ArrayList<>();
        laptopsList.forEach(element -> listString.add(element.getText()));
        Collections.sort(listString, (s1, s2) -> s1.compareToIgnoreCase(s2));
        Collections.sort(listString, Comparator.comparingInt(String::length).thenComparing(String::compareTo));
        String averagePrice = listString.get(listString.size()/2);
        averagePrice = averagePrice.substring(0,averagePrice.indexOf(" "));
        String averageLaptopXPath = String.format("//div[contains(@class,\"catalog-form__description_huge-additional\")]//span[contains(text(),\"%s\")and not(@class)]",averagePrice);
        saveAverageLaptopModelAndPrice(averageLaptopXPath, averagePrice);
        return driver.findElement(By.xpath(averageLaptopXPath));
    }

    private void saveAverageLaptopModelAndPrice(String averageLaptopXPath, String price) {
        averageLaptopXPath = averageLaptopXPath+"//ancestor::div[@class=\"catalog-form__offers-flex\"]//a[contains(text(),\"оутбук\")]";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/java/laptopModel.txt"))) {
            String laptopModel = driver.findElement(By.xpath(averageLaptopXPath)).getText();
            writer.write(laptopModel+" - "+ price);
            propertyReader.setValue("laptop.model", laptopModel);
            propertyReader.setValue("laptop.price", price);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scrollToElementAndClick(WebElement element){
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("arguments[0].click();", element);
    }
    private ArrayList<String> readXmlWithProducers() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse("src/test/java/test/helpers/producers.xml");

            Element filtersElement = document.getDocumentElement();

            NodeList manufacturerNodes = filtersElement.getElementsByTagName("manufacturer");

            ArrayList<String> producersXpaths = new ArrayList<>();

            for (int i = 0; i < manufacturerNodes.getLength(); i++) {
                Element manufacturerElement = (Element) manufacturerNodes.item(i);
                String manufacturer = manufacturerElement.getTextContent();
                String xPath = "//div[text()='" + manufacturer + "' and @class='dropdown-style__checkbox-sign']";
                producersXpaths.add(xPath);
            }
            return producersXpaths;
        } catch (Exception e) {
            return (new ArrayList<>(Collections.singleton("//div[text()='Samsung' and @class='dropdown-style__checkbox-sign']")));
        }
    }
    private void setProducers (ArrayList < String > producersXpaths) {
        for (String xPath : producersXpaths) {
            WebElement producer = driver.findElement(By.xpath(xPath));
            js.executeScript("arguments[0].click();", producer);
        }
    }
    @Step("проверка выбрана ли матрица")
    public boolean checkIfMatrixSelected(String matrixString) {
        WebElement matrixInput = driver.findElement(By.xpath(String.format(CHECK_BOX_XPATH,matrixString)+"/../../input"));
        return matrixInput.isSelected();
    }
    @Step("проверка выбран ли материал")
    public boolean checkIfMaterialSelected(String materialString) {
        WebElement materialInput = driver.findElement(By.xpath(String.format(CHECK_BOX_XPATH,materialString)+"/../../input"));
        return materialInput.isSelected();
    }
    @Step("проверка выбрал ли numpad")
    public boolean checkIfNumpadSelected(String isNumpadNeeded) {
        String value = isNumpadNeeded.equals("Да")?"1":"0";
        WebElement numpadInput = driver.findElement(By.xpath(String.format("//input[@name=\"numpad\" and @value=\"%s\"]",value)));
        return numpadInput.isSelected();
    }
}