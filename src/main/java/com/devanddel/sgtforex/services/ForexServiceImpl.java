package com.devanddel.sgtforex.services;

import com.devanddel.sgtforex.dao.ForexPricesDAO;
import com.devanddel.sgtforex.domain.InstrumentPrice;
import com.devanddel.sgtforex.interfaces.ForexService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * Date: 14/6/21.
 * Author: Dev&Del
 */
@Service
@RequiredArgsConstructor
public class ForexServiceImpl implements ForexService {
    private final ForexPricesDAO forexPricesDAO;

    @Override
    public void loadPrices(String csvLines) {
        forexPricesDAO.loadPrices(csvLines);
    }

    @Override
    public Set<InstrumentPrice> getAllPrices() {
        return forexPricesDAO.getAllPrices();
    }

    @Override
    public Optional<InstrumentPrice> getLatestPrice(@NonNull Integer id) {
        return forexPricesDAO.getLatestPrice(id);
    }
}
