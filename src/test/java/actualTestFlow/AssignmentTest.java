package actualTestFlow;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ReusableMethods.SeleniumCommonMethods;

public class AssignmentTest extends SeleniumCommonMethods {

    WebDriver driver;

    @BeforeTest
    public void setup(){
        SeleniumCommonMethods.get("https://demo.dev.tap.company/v2/sdk/card");
        SeleniumCommonMethods.maximizeWindow();
        SeleniumCommonMethods.selectByValueID("currency", "BHD");
        SeleniumCommonMethods.selectByVisibleTextXpath("//select[@id='scope']", "Authenticated Token");

    }

    @Test(dataProvider = "dataToPass")
    public void enterCardDetails(String cardNo, String expDt, String cvv) throws InterruptedException {
        driver = SeleniumCommonMethods.getDriverInstance();
        SeleniumCommonMethods.navigateIntoFrame("tap-card-iframe", driver);
        SeleniumCommonMethods.waitUntilVisiblityOfElementLocatedByID("card_input_mini", driver);
        SeleniumCommonMethods.enterValueID("card_input_mini", cardNo, driver);
        SeleniumCommonMethods.enterValueCss("input#date_input", expDt);
        SeleniumCommonMethods.enterValueCss("input[name='cvv_input']", cvv);
        Thread.sleep(1000);
        SeleniumCommonMethods.setAttributeValueXpath("//input[@id='cardHolderName_input']", "TestingCard");
        Thread.sleep(1000);
        SeleniumCommonMethods.navigateToDefaultContent();
        SeleniumCommonMethods.clickButton("//button[contains(text(),'Create Token')]");
        SeleniumCommonMethods.waitAndSwitchIntoFrame("tap-card-iframe", driver);
        SeleniumCommonMethods.navigateIntoFrame("tap-card-iframe-authentication", driver);
        Thread.sleep(2000);
        if(cardNo.contains("51234")){
            SeleniumCommonMethods.waitAndSwitchIntoFrame("challengeFrame", driver);
            SeleniumCommonMethods.waitUntilVisiblityOfElementLocatedByID("acssubmit", driver);//not worked then create xpath method
            SeleniumCommonMethods.submitButton("//input[@id='acssubmit']");
        } else if(cardNo.contains("45087")){
            SeleniumCommonMethods.waitAndSwitchIntoFrame("redirectTo3ds1Frame", driver);
            SeleniumCommonMethods.waitUntilVisiblityOfElementLocatedByXpath("//input[@type='submit']", driver);//not worked then create xpath method
            SeleniumCommonMethods.submitButton("//input[@type='submit']");
        }
        SeleniumCommonMethods.navigateToDefaultContent();
        SeleniumCommonMethods.clickButton("//input[contains(@id,'Response')]");
        SeleniumCommonMethods.waitElementClickableXpath("//*[local-name()='svg' and @class='copyButtonIcon_CtfL']/*[local-name()='path']");
        SeleniumCommonMethods.clickButtonUsingActions("//*[local-name()='svg' and @class='copyButtonIcon_CtfL']/*[local-name()='path']");
        SeleniumCommonMethods.storeAndPrintCopiedContent();
        SeleniumCommonMethods.clickButton("//button[contains(text(),'Clear Log')]");
    }

    @DataProvider(name = "dataToPass")
    public Object[][] dataPassing(){
        return new Object[][]{
                {"5123450000000008", "01/39", "100"},
                {"4508750015741019", "01/39", "100"}
        };
    }


}
