import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import CsvDataProviders.CsvDataProviders;

import java.util.Map;

public class TestPlan extends Utils {
    protected  WebDriver driver;
    protected Logger log;

    @Parameters({ "browser" })

    @BeforeSuite(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser, ITestContext ctx) {

    String testName = ctx.getCurrentXmlTest().getName();
    log= LogManager.getLogger(testName);


    DriverFactory factory = new DriverFactory(browser,log);
    driver = factory.createDriver();
    driver.manage().window().maximize();
    }


    @Test(dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class,priority = 1,testName = "Search on Google")
    public void search(Map<String, String> testData){

        //****Data Driven Test****

        String no = testData.get("no");
        String  username = testData.get("username");
        String password = testData.get("password");
        String expectedMessage =testData.get("expectedMessage");
        String description = testData.get("description");

        log.info("Opening the page: "+ Utils.BASE_URL);
        log.info("Starting data test: "+ no+"for"+description);
        driver.get(Utils.TEST1_URL);
        LoginPageObjects page = new LoginPageObjects(driver,log);
        PageFactory.initElements(driver, page);
        log.info("The page has been opened.");
        page.search();

    }

    @Test (priority = 2,testName = "Checkbox control")
        public void checkboxes(){
        log.info("Opening the page:" + Utils.BASE_URL);
        driver.get(Utils.BASE_URL);
        LoginPageObjects page = new LoginPageObjects(driver,log);
        PageFactory.initElements(driver,page);
        page.checkboxes();
        Assert.assertTrue(page.assertionCheckboxes(),"Not all selected checkboxes are checked.");
    }

    @Test (priority =3, testName = "Alert handling test")
    public void Alert(){

log.info("Opening the page:"+Utils.ALERT_URL);
log.info("Starting the JS Alert test");
driver.get(Utils.ALERT_URL);
LoginPageObjects page = new LoginPageObjects(driver,log);
PageFactory.initElements(driver,page);
//click the alert button
page.switchToAlert();
//get the alert message
page.getAlertText();
//assertion of the alert text
page.assertionAlert();
//accept and close the alert
page.acceptAlert();
    }

@Test (dataProvider = "files", priority = 4,testName = "Upload a file")
        public void uploadFile(int testno, String file_name){
        log.info("Opening the page"+Utils.UPLOADING_URL);
        log.info("Starting upload file test No:" +testno+ "for"+ file_name);
        driver.get(Utils.UPLOADING_URL);
        LoginPageObjects page = new LoginPageObjects(driver,log);
        PageFactory.initElements(driver,page);
        page.chooseFiles(file_name);
        page.imageUploadTest();

    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws InterruptedException {
        Thread.sleep(4000);
        log.info("Close driver");
        driver.manage().deleteAllCookies();
        driver.close();
    }

}