import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class TestPlan {


    protected  WebDriver driver;
    protected Logger log;
    String file_name ="pic.jpeg";

    @Parameters({ "browser" })

    @BeforeSuite(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser, ITestContext ctx) {

    String testName = ctx.getCurrentXmlTest().getName();
    log= LogManager.getLogger(testName);


    DriverFactory factory = new DriverFactory(browser,log);
    driver = factory.createDriver();
    driver.manage().window().maximize();


    }


    @Test(priority = 1,testName = "Search on Google")

    public void search(){

        log.info("Opening the page: "+ Utils.BASE_URL);
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

@Test (priority = 4,testName = "Upload a file")
        public void uploadFile(){
        log.info("Opening the page"+Utils.UPLOADING_URL);
        log.info("Starting upload file test");
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