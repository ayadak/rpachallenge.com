package se.ayad.rpachallenge;

import org.apache.commons.csv.CSVRecord;
import java.util.List;

public interface RPAChallengeAutomation {
    void launchWebsite(String url);
    void fillAndSubmitForm(List<CSVRecord> records);
    double extractAndPrintResults();
    void close();
}