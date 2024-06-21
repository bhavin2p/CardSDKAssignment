package ReusableMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;

public class SeleniumCommonMethods {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Select select;
    public static Actions action;
    public static Toolkit toolkit = Toolkit.getDefaultToolkit();
    public static Clipboard clipboard = toolkit.getSystemClipboard();

    public static void get(String url){
        driver = new ChromeDriver();
        driver.get(url);
    }

    public static WebDriver getDriverInstance(){
        return driver;
    }

    public static void maximizeWindow(){
        driver.manage().window().maximize();
    }

    public static void waitAndSwitchIntoFrame(String id, WebDriver driver){
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
       wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(id));
    }

    public static void waitUntilVisiblityOfElementLocatedByID(String idVal, WebDriver driver){
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idVal)));
    }

    public static void waitUntilVisiblityOfElementLocatedByXpath(String xpathVal, WebDriver driver){
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVal)));
    }

    public static void setAttributeValueXpath(String locXpath, String valueToEnter){
        String attVal = driver.findElement(By.xpath(locXpath)).getAttribute("value");
        if(attVal.isEmpty()){
            driver.findElement(By.xpath(locXpath)).sendKeys(valueToEnter);
        }
    }

    public static void enterValueID(String locId, String valueToEnter, WebDriver driver){
        driver.findElement(By.id(locId)).sendKeys(valueToEnter);
    }

    public static void enterValueCss(String locCss, String valueToEnter){
        driver.findElement(By.cssSelector(locCss)).sendKeys(valueToEnter);
    }

    public static void navigateIntoFrame(String id, WebDriver driver){
        driver.switchTo().frame(id);
    }

    public static void navigateToDefaultContent(){
        driver.switchTo().defaultContent();
    }

    public static void selectByVisibleTextXpath(String visibleText, String visibleVal){
        select = new Select(driver.findElement(By.xpath(visibleText)));
        select.selectByVisibleText(visibleVal);
    }

    public static void selectByValueID(String valueText, String valueVal){
        select = new Select(driver.findElement(By.id(valueText)));
        select.selectByVisibleText(valueVal);
    }

    public static void clickButton(String locXpath){
        driver.findElement(By.xpath(locXpath)).click();
    }

    public static void submitButton(String locXpath){
        driver.findElement(By.xpath(locXpath)).submit();
    }

    public static void waitElementClickableXpath(String locXpath){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locXpath)));
    }
    public static void clickButtonUsingActions(String locXpath){
        action = new Actions(driver);
        action.click(driver.findElement(By.xpath(locXpath))).build().perform();
    }

    public static void storeAndPrintCopiedContent() {
        String actualCopiedText = null;
        try{
            actualCopiedText = (String)clipboard.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("JSON Response Received = " +actualCopiedText);
    }
}
