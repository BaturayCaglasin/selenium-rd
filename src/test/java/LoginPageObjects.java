import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.layout.internal.ListChecker;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;


public class LoginPageObjects extends TestPlan {


    public LoginPageObjects(WebDriver driver, Logger log){


        this.driver=driver;
        this.log=log;
    }

    //locators - Driver Factory (POM)

    @FindBy(how = How.NAME,using = "q")
    private WebElement SEARCH_AREA;

    @FindBy(how = How.ID,using="checkboxes")
    private List <WebElement> CHECKBOXES;

    @FindBy(how=How.XPATH,using="//div[@id='content']//ul//button[.='Click for JS Alert']")
    private WebElement ALERTBUTTON;

    @FindBy(how=How.ID,using="file-upload")
    private WebElement CHOOSEFIELDLOCATOR;

    @FindBy(how=How.ID,using="file-submit")
    private WebElement UPLOADBUTTONLOCATOR;

    @FindBy(how=How.ID,using="uploaded-files")
    private WebElement UPLOADFILESLOCATOR;


    //1st test
    public void search() {
        log.info("The first test is initiated.");
        String SEARCH_KEY = "Batu";
        SEARCH_AREA.sendKeys(SEARCH_KEY);
    }

    //List'te bir sorun var.
    //2nd test
    public void checkboxes(){
        log.info("The second test is initiated.");
        //for all checkboxes List <WebElement>
        //select all checkboxes. If there is some checkboxes that are clicked; not click them.
        for (WebElement CHECKBOXES:CHECKBOXES){
            if (!CHECKBOXES.isSelected()){
                CHECKBOXES.click();
            }
        }
    }

    //Assertion of checkbox click
    protected boolean assertionCheckboxes(){
        log.info("The assertion is initiated. It is verifying that all checkboxes are clicked.");
        for (WebElement CHECKBOXES:CHECKBOXES){
            if (!CHECKBOXES.isSelected()){
                return false;
            }
        }
        return true;
    }

    //*****ALERT*****

    //Alert popup
    protected Alert switchToAlert(){
        ALERTBUTTON.click();
        WebDriverWait wait = new WebDriverWait (driver,10);
wait.until(ExpectedConditions.alertIsPresent());
return driver.switchTo().alert();
    }

    protected String getAlertText(){
        Alert alert = switchToAlert();
        String alertText=alert.getText();
        log.info("Alert says:"+alertText);
        return alertText;
}
    protected void acceptAlert(){
        log.info("Switching to alert and click OK.");
        Alert alert = switchToAlert();
        alert.accept();

    }
    protected void assertionAlert() {
        log.info("Assertion of the alert");
        Alert alert = switchToAlert();
        String alertMessage = getAlertText();
        Assert.assertEquals(alertMessage, "I am a JS Alert", "Alert message is not expected. Should be: I am a JS Alert");
    }

    //*****UPLOADING FILES*****

    //choosing the files
protected void type(String text,WebElement CHOOSEFIELDLOCATOR){
    CHOOSEFIELDLOCATOR.sendKeys(text);
}
public void chooseFiles(String file_name){
log.info("Selecting the files"+file_name);
String FILE_PATH=System.getProperty("user.dir")+"//src//main//resources//"+file_name;
type(FILE_PATH,CHOOSEFIELDLOCATOR);
log.info("File is selected");

    }

// uploading files
        public void imageUploadTest(){
            log.info("Clicking the upload button");
            UPLOADBUTTONLOCATOR.click();
            log.info("Clicked the upload button");

        }
    }
