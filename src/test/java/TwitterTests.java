import org.junit.*;
import org.junit.runners.MethodSorters;
import pages.Home;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TwitterTests {

    private static String statusMsg = "Twitter test message Selenium Tests";
    private static Home home;
    private static Date createdDate;

    @BeforeClass
    public static void prepare() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "webdriver/chromedriver.exe");

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
            home = new Home();
            home.open();
            if (!home.amILoggedIn()) {
                home.fillInLoginForm(prop.getProperty("username"), prop.getProperty("password"));
            }
            if(home.isStatusExist(statusMsg))
            {
                home.destroyStatus(statusMsg);
            }
            createdDate = new Date();
            home.updateStatus(statusMsg);

    }

    @Test
    public void test01_update_StatusShouldHaveCorrectDate() throws ParseException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a - dd MMM yyyy");
        Date date = sdf.parse(home.getStatusCreationDate(statusMsg));
        assertEquals(createdDate.getTime(), date.getTime(), 13);
    }

    @Test
    public void test02_retweet_ShouldIncreaseRetweetCount() throws ParseException, InterruptedException {
        Assert.assertEquals(1, home.getGetRetweetCount(statusMsg));
    }

    @Test
    public void test03_status_ShouldHaveProperText() throws InterruptedException {
        assertTrue(home.isStatusExist(statusMsg));
    }

    @Test
    public void test04_updateStatusWithSameMsg_ShouldDisplayException() throws InterruptedException {
        home.updateStatus(statusMsg);
        assertTrue(home.isErrorMessageDisplayed());
    }

    @Test
    public void test05_destroyStatus_ShouldRemoveStatus() throws InterruptedException {
        home.destroyStatus(statusMsg);
        assertFalse(home.isStatusExist(statusMsg));
    }
}