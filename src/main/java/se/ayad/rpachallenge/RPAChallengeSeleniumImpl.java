package se.ayad.rpachallenge;

import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RPAChallengeSeleniumImpl implements RPAChallengeAutomation {
    private final WebHandling webHandling;
    private final WebDriver driver;

    public RPAChallengeSeleniumImpl(WebHandling webHandling) {
        this.webHandling = webHandling;
        this.driver = webHandling.getDriver();
    }

    @Override
    public void fillAndSubmitForm(List<CSVRecord> records) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Start the challenge by clicking the start button
        WebElement startButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Start')]")));
        startButton.click();

        for (CSVRecord record : records) { // This iterates over each row
            for (String header : record.toMap().keySet()) { // This iterates over each column header
                WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(), '" + header + "')]/following-sibling::input")));
                inputField.clear();
                inputField.sendKeys(record.get(header).trim());
            }

            // Click the submit button
            WebElement submitButton = driver.findElement(By.cssSelector("input[type='submit']"));
            submitButton.click();
        }
    }

    @Override
    public double extractAndPrintResults() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement resultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.message2")));
        String resultText = resultElement.getText();
        double soSlow = Double.valueOf(resultText.replaceAll(".*in (\\d+) milliseconds.*", "$1")) / 1000;
        resultText = resultText.replaceAll("in (\\d+) milliseconds", "");
        System.out.println(resultText + "\nRPAchallenge från start till slut tog " + soSlow + "s med Selenium");
        return soSlow;
    }

    @Override
    public void launchWebsite(String url) {
        webHandling.launchWebsite(url);
    }

    @Override
    public void close() {
        // No need to close the driver here since WebHandling manages it
    }
}