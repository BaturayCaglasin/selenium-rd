// log4j for logging
//dataprovider for data driven framework
//@Listener annotation for test listener. Should add a listener class.

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import CsvDataProviders.CsvDataProviders;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.Map;

public class TestPlan {
    protected  WebDriver driver;
    protected Logger log;

    protected String testSuiteName;
    protected String testName;
    protected String testMethodName;

    @DataProvider(name="files")
    protected static Object[][] files(){
        return new Object[][]{
                {1,"pic.jpeg"}
        };

    }

    @Parameters({ "browser" })

    @BeforeSuite(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser, ITestContext ctx) {

    String testName = ctx.getCurrentXmlTest().getName();
    log= LogManager.getLogger(testName);


    DriverFactory factory = new DriverFactory(browser,log);
    driver = factory.createDriver();
    driver.manage().window().maximize();

    this.testSuiteName=ctx.getSuite().getName();
    this.testName=testName;
    //this.testMethodName=method.getName();


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

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals("File Uploaded!","File Uploaded!");
        log.info("File is uploaded.");
         Utils utils = new Utils(driver,log);
        utils.takeScreenshot("File Uploaded!");

    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws InterruptedException {
        Thread.sleep(4000);
        log.info("Close driver");
        driver.manage().deleteAllCookies();
        driver.close();
    }

    //***ASSERTIONS***

    //soft assert; test esnasında hata meydana geldiğinde bunu log'lar ve test bitene kadar step'lerin akışı devam eder.
    //hard assert ise test esnasında bir hata meydana geldiği zaman direkt hata fırlatarak testi sonlandırır ve bir sonraki teste devam edilir.


}