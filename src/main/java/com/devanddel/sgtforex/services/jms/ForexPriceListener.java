package com.devanddel.sgtforex.services.jms;

import com.devanddel.sgtforex.interfaces.ForexService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Date: 14/6/21.
 * Author: Carlos Cuesta - Dev&Del
 */
@Component
@RequiredArgsConstructor
public class ForexPriceListener {

    private final ForexService forexService;

    void onMessage(String message) {
        forexService.loadPrices(message);
    }
}
