package se.ayad.rpachallenge;

import se.ayad.rpachallenge.RPAChallengeJsImpl;
import se.ayad.rpachallenge.RPAChallengeSeleniumImpl;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVRecord;
import java.time.Instant;
import java.time.Duration;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Update the path to the correct location of challenge.csv
        String csvFilePath = Paths.get("src/main/resources/challenge.csv").toString();
        CSVReader csvReader = new CSVReaderImpl();

        WebHandling webHandling = new WebHandling(); // Create a single WebHandling instance

        try {
            // Launch the website only once
            webHandling.launchWebsite("https://rpachallenge.com/");

            // Read data from CSV
            List<CSVRecord> records = csvReader.readCSV(csvFilePath);

            // Run Selenium Implementation first
            RPAChallengeAutomation seleniumRPAChallenge = new RPAChallengeSeleniumImpl(webHandling);

            // Record the start time for Selenium Implementation
            Instant seleniumStartTime = Instant.now();

            // Fill and submit the form using Selenium
            seleniumRPAChallenge.fillAndSubmitForm(records);


            // Extract and print results using Selenium
            double soSlow = seleniumRPAChallenge.extractAndPrintResults();

            // Record the end time for Selenium Implementation
            Instant seleniumEndTime = Instant.now();

            // Calculate duration for Selenium
            Duration seleniumDuration = Duration.between(seleniumStartTime, seleniumEndTime);
            double seleniumSeconds = seleniumDuration.toMillis() / 1000.0;
            System.out.printf("Selenium-filen tog %.3f s att köra\n", seleniumSeconds);

            // Press the reset button before running JavaScript implementation
            webHandling.resetChallenge();

            // Run JavaScript Implementation
            RPAChallengeAutomation rpaChallenge = new RPAChallengeJsImpl(webHandling);

            // Record the start time for JavaScript Implementation
            Instant jsStartTime = Instant.now();

            // Fill and submit the form using JavaScript
            rpaChallenge.fillAndSubmitForm(records);

            // Extract and print results
            double soFast = rpaChallenge.extractAndPrintResults();

            // Record the end time for JavaScript Implementation
            Instant jsEndTime = Instant.now();

            // Calculate duration for JavaScript
            Duration jsDuration = Duration.between(jsStartTime, jsEndTime);
            double jsSeconds = jsDuration.toMillis() / 1000.0;
            System.out.printf("Js-filen tog %.3f s att köra\n", jsSeconds);
            double timeDifference = seleniumSeconds - jsSeconds;
            double speedRatio = seleniumSeconds / jsSeconds;
            // Calculate performance difference
            double rpaDifference = soSlow - soFast;
            double rpaRatio = soSlow / soFast;
            System.out.printf("JavaScript är betydligt snabbare än Selenium på att hantera RPAChallenge.com\nJs-kod är i detta fall %.3f sekunder och %.2f ggr snabbare än Selenium" +
                            "\nPå den tid med Selenium fyllt i 70 fält hinner Js mata in ca: %d fält fler" +
                            "\näven om vi räknar med tiden inklusive resp. metodanrop, så är JavaScript aka Speedy Gonzales %.3f sekunder och %.2f gånger snabbare än SEEGenium"
                    , rpaDifference, rpaRatio, ((int) rpaRatio * 70)-70, timeDifference, speedRatio);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        // Close the browser after all tasks are completed
        webHandling.closeDriver();
    }
}