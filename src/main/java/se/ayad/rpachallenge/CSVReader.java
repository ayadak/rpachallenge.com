package se.ayad.rpachallenge;

import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVRecord;

public interface CSVReader {
    List<CSVRecord> readCSV(String filePath) throws IOException;
}
