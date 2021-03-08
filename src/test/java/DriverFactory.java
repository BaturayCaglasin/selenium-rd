import org.apache.logging.log4j.Logger;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.ThreadLocalRandom;

public class DriverFactory {


    private ThreadLocal <WebDriver> driver = new ThreadLocal<WebDriver>();
    private String browser;
    private Logger log;

    public DriverFactory(String browser, Logger log){
        this.browser=browser.toLowerCase();
        this.log=log;
    }
    public WebDriver createDriver(){
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);

       log.info("Create driver: " + browser);
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", Utils.CHROME_DRIVER_LOCATION);
                driver.set(new ChromeDriver(capabilities));
                break;

            case "chromeHeadless":

                System.setProperty("webdriver.chrome.driver",Utils.CHROME_DRIVER_LOCATION);
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                driver.set(new ChromeDriver(capabilities));
                break;


            case "firefox":
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
               driver.set(new FirefoxDriver());
                break;

            case "firefoxheadless":
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                firefoxBinary.addCommandLineOptions("--headless");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);
                driver.set(new FirefoxDriver());
                break;


            case "phantomjs":
                //System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver.set(new FirefoxDriver()); //new PhantomJSDriver
                break;

            case "htmlunit":
                //System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver.set(new FirefoxDriver()); //new HtmlUnitDriver
                break;

            default:
                System.out.println("Do not know how to start: " + browser + ", starting chrome.");
                System.setProperty("webdriver.chrome.driver",  Utils.CHROME_DRIVER_LOCATION);
                driver .set(new ChromeDriver());
                break;
        }
        return driver.get();
    }

}