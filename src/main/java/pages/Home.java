package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home extends Page {

    private static final String URl = "https://twitter.com/?lang=e";
    private static final String USER_HOME_URl = "https://twitter.com/ZaRk8n";
    WebDriverWait wait = new WebDriverWait(driver, 30);

    public void open() throws InterruptedException {
        open(URl);
    }

    public void fillInLoginForm(String userName, String password) throws InterruptedException {
        wait.until(ExpectedConditions.titleIs("Twitter. It's what's happening."));
        $(By.cssSelector("#signin-email")).sendKeys(userName);
        $(By.cssSelector("#signin-password")).sendKeys(password);
        $(By.cssSelector("#front-container > div.front-card > div.front-signin.js-front-signin > form > table > tbody > tr > td.flex-table-secondary > button")).click();
        if (!amILoggedIn()) {
            $(By.cssSelector("#challenge_response")).sendKeys("0631440037");
            $(By.cssSelector("#email_challenge_submit")).click();
        }
    }

    public boolean amILoggedIn() {
        try {
            $(By.cssSelector("#page-container > div.dashboard.dashboard-left > div.DashboardProfileCard.module > div > div.DashboardProfileCard-userFields.account-group > div > a")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public void destroyStatus(String message) throws InterruptedException {
        synchronized (driver) {
            driver.wait(1000);
            open(USER_HOME_URl);
            driver.wait(1000);
        }
        $(By.xpath("//*[contains(text(), '" + message + "')]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"permalink-overlay-body\"]/div/div[1]/div[1]/div/div[1]/div/div/div/button"))).click();
        $(By.xpath("//*[@id=\"permalink-overlay-body\"]/div/div[1]/div[1]/div/div[1]/div/div/div/div/ul/li[8]/button")).click();
        $(By.xpath("//*[@id=\"delete-tweet-dialog-dialog\"]/div[2]/div[4]/button[2]")).click();
    }

    public void updateStatus(String message) throws InterruptedException {
        synchronized (driver) {
            driver.wait(2000);
        }
        $(By.xpath("//*[@id=\"global-new-tweet-button\"]")).click();
        $(By.xpath("//*[@id=\"tweet-box-global\"]")).sendKeys(message);
        $(By.xpath("//*[@id=\"global-tweet-dialog-dialog\"]/div[2]/div[4]/form/div[3]/div[2]/button/span[1]")).click();
    }

    public boolean isStatusExist(String message) throws InterruptedException {
        try {
            open(USER_HOME_URl);
            $(By.xpath("//*[contains(text(), '" + message + "')]"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean isErrorMessageDisplayed() throws InterruptedException {
        try {
            $(By.xpath("//*[@id=\"message-drawer\"]/div/div/span"));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public String getStatusCreationDate(String message) throws InterruptedException {
        synchronized (driver) {
            driver.wait(2000);
        }
        $(By.xpath("//*[contains(text(), '" + message + "')]")).click();
        synchronized (driver) {
            driver.wait(1000);
        }
        return $(By.xpath("//*[@id=\"permalink-overlay-body\"]/div/div[1]/div[1]/div/div[3]/div[1]/span/span")).getText();
    }

    public int getGetRetweetCount(String message) throws InterruptedException {
        synchronized (driver) {
            open(USER_HOME_URl);
            driver.wait(2000);
        }
        $(By.xpath("//*[contains(text(), '" + message + "')]")).click();
        synchronized (driver) {
            driver.wait(1000);
        }
        $(By.xpath("//*[@id=\"permalink-overlay-body\"]/div/div[1]/div[1]/div/div[4]/div[2]/div[2]/button[1]")).click();
        $(By.xpath("//*[@id=\"retweet-tweet-dialog-dialog\"]/div[2]/form/div[2]/div[3]/button")).click();
        synchronized (driver) {
            driver.wait(1000);
        }
        String[] retweetedText = $(By.xpath("//*[@id=\"permalink-overlay-body\"]/div/div[1]/div[1]/div/div[4]/div[2]/div[2]/button[2]")).getText().split("\n");
        return Integer.parseInt(retweetedText[retweetedText.length - 1]);
    }
}