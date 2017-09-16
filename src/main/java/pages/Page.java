package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Page {

    WebDriver driver = new ChromeDriver();

    public Page() {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    protected void open(String url) {
        driver.get(url);
    }


    protected WebElement $(String xpath, String... args) {
        return driver.findElement(By.xpath(String.format(xpath, args)));
    }


    protected List<WebElement> $$(By by) {
        return driver.findElements(by);
    }

    protected WebElement $(By by) {
        return driver.findElement(by);
    }
}
