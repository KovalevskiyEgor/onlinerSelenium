package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import pages.BasePage;
import java.io.*;


public class Screenshoter extends BasePage {
    public static void takeScreenshot(String screenName){
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, new File(String.format("src/test/java/screenshots/%s.png",screenName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}