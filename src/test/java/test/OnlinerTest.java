package test;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.*;

public class OnlinerTest extends BaseTest{
    @Test
    @Owner("Ковалевский Егор")
    @Severity(SeverityLevel.NORMAL)
    @Description("е2е тестируем онлайнер")
    @Parameters({"marketLaunchDateFrom","marketLaunchDateTo","matrix","material","isNumpadNeeded"})
    public void e2eTest(String marketLaunchDateFrom, String marketLaunchDateTo,String matrix,String material, String isNumpadNeeded){
        SoftAssert softAssert = new SoftAssert();
        MainPage mainPage = new MainPage();
        mainPage.clickOnLaptopButton();

        LaptopsPage laptopsPage = new LaptopsPage();
        laptopsPage.setProducers();
        softAssert.assertTrue(laptopsPage.checkIfProducersSelected(),"выбраны ли прозводители");
        laptopsPage.setMarketLaunchDate(marketLaunchDateFrom,marketLaunchDateTo);
        softAssert.assertTrue(laptopsPage.checkIfMarketDateSet(marketLaunchDateFrom, marketLaunchDateTo),"выбран ли год выхода");
        laptopsPage.setMatrix(matrix);
        softAssert.assertTrue(laptopsPage.checkIfMatrixSelected(matrix),"выбрана ли матрица");
        laptopsPage.setMaterial(material);
        softAssert.assertTrue(laptopsPage.checkIfMaterialSelected(material),"выбран ли материал");
        laptopsPage.setNumpad(isNumpadNeeded);
        softAssert.assertTrue(laptopsPage.checkIfNumpadSelected(isNumpadNeeded),"выбран ли numpad");
        laptopsPage.clickOnAverageLaptop();

        LaptopInformationPage laptopInformationPage = new LaptopInformationPage();
        laptopInformationPage.addLaptopToBasket();
        Assert.assertTrue(laptopInformationPage.checkIfItsRightLaptop());
        laptopInformationPage.goToBasket();

        BasketPage basketPage = new BasketPage();
        basketPage.goToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage();
        softAssert.assertTrue(checkoutPage.checkIfPriceCorrect(),"цена правильно посчитана");
        checkoutPage.setTown("Минск");
        checkoutPage.setStreet("ул. Орловская");
        checkoutPage.setHouse("10");
        checkoutPage.setCorpus("1");
        checkoutPage.setGate("4");
        checkoutPage.setFloor("1");
        checkoutPage.setFlat("200");
        checkoutPage.setComment("Извиняюсь за кривые xpaсы привязаться вообще не нашел к чему :(");
        checkoutPage.setName("Егор");
        checkoutPage.setSurname("Ковалевский");
        checkoutPage.setEmail("kovalevskiy_egor@mail.ru");
        checkoutPage.goToPayment();

        softAssert.assertAll();
    }
}