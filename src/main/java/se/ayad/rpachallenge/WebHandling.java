package se.ayad.rpachallenge;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebHandling {
    private WebDriver driver;
    private WebDriverWait wait;

    public WebHandling() {
        System.setProperty("webdriver.chrome.driver", "C:\\Code\\chromedriver\\chromedriver-win64\\chromedriver.exe"); // Adjust chromedriver path
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        this.driver = new ChromeDriver(options);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void launchWebsite(String url) {
        driver.get(url);
    }

    public void resetChallenge() {
        // Locate the reset button and click it to reset the challenge
        WebElement resetButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Reset')]")));
        resetButton.click();
    }

    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}