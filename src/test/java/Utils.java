import org.testng.annotations.DataProvider;

public class Utils  {

final static String BASE_URL = "http://the-internet.herokuapp.com/checkboxes";
final static String TEST1_URL="https://www.google.com.tr/";
final static String ALERT_URL="http://the-internet.herokuapp.com/javascript_alerts";
final static String UPLOADING_URL="http://the-internet.herokuapp.com/upload";
final static String CHROME_DRIVER_LOCATION="src/test/java/chromedriver.exe";

@DataProvider(name="files")
protected static Object[][] files(){
    return new Object[][]{
            {1,"pic.jpeg"}
    };
}
}
