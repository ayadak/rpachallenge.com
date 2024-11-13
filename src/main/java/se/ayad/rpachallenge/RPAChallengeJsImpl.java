package se.ayad.rpachallenge;

import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class RPAChallengeJsImpl implements RPAChallengeAutomation {
    private final WebHandling webHandling;
    private final WebDriver driver;

    public RPAChallengeJsImpl(WebHandling webHandling) {
        this.webHandling = webHandling;
        this.driver = webHandling.getDriver();
    }

    @Override
    public void fillAndSubmitForm(List<CSVRecord> records) {
        StringBuilder jsCode = new StringBuilder();
        jsCode.append("document.querySelector('button').click();");

        for (CSVRecord record : records) {
            for (String header : record.toMap().keySet()) {
                jsCode.append("$('div:contains(\"").append(header).append("\") > input').val('")
                        .append(record.get(header).trim()).append("');");
            }
            jsCode.append("$('input[type=\"Submit\"]').click();");
        }

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript(jsCode.toString());
    }

    @Override
    public double extractAndPrintResults() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String resultText = (String) jsExecutor.executeScript("return document.querySelector('div.message2').innerText;");
        double soFast = Double.valueOf(resultText.replaceAll(".*in (\\d+) milliseconds.*", "$1")) / 1000;
        resultText = resultText.replaceAll("in (\\d+) milliseconds", "");
        System.out.println(resultText + "\nRPAchallenge från start till slut tog " + soFast + "s med Js-kod");
        return soFast;
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