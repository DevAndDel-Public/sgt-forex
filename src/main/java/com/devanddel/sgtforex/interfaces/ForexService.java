package com.devanddel.sgtforex.interfaces;

import com.devanddel.sgtforex.domain.InstrumentPrice;

import java.util.Optional;
import java.util.Set;

/**
 * Date: 14/6/21.
 * Author: Dev&Del
 */
public interface ForexService {
    void loadPrices(String csvLines);

    Set<InstrumentPrice> getAllPrices();

    Optional<InstrumentPrice> getLatestPrice(Integer id);
}
