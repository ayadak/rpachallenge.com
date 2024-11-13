package se.ayad.rpachallenge;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CSVReaderImpl implements CSVReader {
    @Override
    public List<CSVRecord> readCSV(String filePath) throws IOException {
        // Initialize the Reader and CSVParser directly
        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withDelimiter(',')
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim())) {
            return csvParser.getRecords();
        }
    }
}