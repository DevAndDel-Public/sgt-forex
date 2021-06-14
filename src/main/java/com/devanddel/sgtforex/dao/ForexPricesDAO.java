package com.devanddel.sgtforex.dao;

import com.devanddel.sgtforex.domain.InstrumentPrice;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.devanddel.sgtforex.Constants.CSV_TIMESTAMP_PATTERN;

/**
 * Date: 14/6/21.
 * Author: Carlos Cuesta - Dev&Del
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ForexPricesDAO {
    final ConcurrentHashMap<Integer, InstrumentPrice> currentPrices = new ConcurrentHashMap<>();


    /**
     * Loads instrument prices in CSV format
     * @param csvString line/s in CSV format (without header)
     *                 csv fields
     *                 unique id, instrument name, bid, ask and timestamp
     */
    public void loadPrices(String csvString) {
        try (final Reader reader = new StringReader(csvString);
             final CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord csvRecord : parser) {
                try {
                    InstrumentPrice insprice = InstrumentPrice.builder()
                            .id(Integer.parseInt(csvRecord.get(0)))
                            .name(csvRecord.get(1))
                            .bid(Double.parseDouble(csvRecord.get(2)))
                            .ask(Double.parseDouble(csvRecord.get(3)))
                            .timestamp(parseFromString(csvRecord.get(4)))
                            .build();
                    insprice.setMargins();
                    currentPrices.put(insprice.getId(), insprice);
                } catch (NullPointerException npe) {
                    npe.printStackTrace(); // TODO log null Id...
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace(); // TODO log bad id, bid or ask format...
                } catch (DateTimeParseException dtpe) {
                    dtpe.printStackTrace(); // TODO log bad timestamp string...
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace(); // TODO log csvString i/o exception
        }
    }

    /**
     * @param timeStampString string containing the timestamp in CSV_TIMESTAMP_PATTERN format
     * @return resulting LocalDateTime from parsing
     */
    private LocalDateTime parseFromString(String timeStampString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CSV_TIMESTAMP_PATTERN);
        return LocalDateTime.parse(timeStampString, formatter);
    }

    /**
     * @return collection with all instrument prices
     */
    public Set<InstrumentPrice> getAllPrices() {
        return new HashSet<>(currentPrices.values());
    }

    /**
     * @param id instrument Id
     * @return Optional of InstrumentPrice with key id or null
     */
    public Optional<InstrumentPrice> getLatestPrice(Integer id) {
        return Optional.ofNullable(currentPrices.get(id));
    }

}
