package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepDef.PageInitializer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommonMethods extends PageInitializer{
    public static WebDriver driver;
    public static Actions actions;

    public static JavascriptExecutor javascriptExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;
    }

    public static void openBrowserAndLaunchApplication() {
        ConfigReader.readProperties();
        String browserType = ConfigReader.getPropertyValue("browserType");
        switch (browserType) {
            case "Chrome" -> driver = new ChromeDriver();
            case "Firefox" -> driver = new FirefoxDriver();
            case "IE" -> driver = new InternetExplorerDriver();
            default -> driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        driver.get(ConfigReader.getPropertyValue("url"));
        driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);
        initializePageObjects();
    }




    public static void closeBrowser(){
        driver.quit();
    }


    public static void waitForClickability(WebElement element){
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void doClick(WebElement element){
        waitForClickability(element);
        element.click();
    }

    public static void sendText (WebElement element, String text){
        element.clear();
        element.sendKeys(text);
    }

    public static Select clickDropDown(WebElement element){
        return new Select(element);
    }

    public static void selectByValue(WebElement element,String value) {
        clickDropDown(element).selectByValue(value);
    }
    public static void selectByVisibleText(WebElement element,String text) {
        clickDropDown(element).selectByVisibleText(text);
    }
    public static void selectByIndex(WebElement element,int index) {
        clickDropDown(element).selectByIndex(index);
    }

    public static WebDriverWait getWait(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
        return wait;
    }

    public static void selectByOptions(WebElement element,String text) {
        List<WebElement> options= clickDropDown(element).getOptions();
        for (WebElement option : options) {
            String dropDownListOptionText= option.getText();
            if(dropDownListOptionText.equals(text)){
                option.click();
            }
        }
    }

    public static byte[] takeScreenshot(String imageName){
        TakesScreenshot ts= (TakesScreenshot) driver;
        byte[] picBytes=ts.getScreenshotAs(OutputType.BYTES);
        File sourcePath=ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(sourcePath, new File(Constants.SCREENSHOT_FILEPATH+imageName+getTimeStamp("yyyy-MM-dd-HH-mm-ss")+".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return picBytes;
    }

    public static String getTimeStamp(String pattern){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static void actionClick(WebElement element){
        actions = new Actions(driver);
        actions.click().build().perform();
    }
    public static void hoverOver(WebElement element){
        actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    public static void actionsScroll(WebElement element){
        actions.moveToElement(element).perform();
    }

    public static void jsClick(WebElement element){
        javascriptExecutor().executeScript("arguments[0].click();", element);
    }


}
