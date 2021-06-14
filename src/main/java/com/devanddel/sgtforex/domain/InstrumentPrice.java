package com.devanddel.sgtforex.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static com.devanddel.sgtforex.Constants.COMMISSION_PERCENT;

/**
 * Date: 14/6/21.
 * Author: Carlos Cuesta - Dev&Del
 */
@Data
@Builder
public class InstrumentPrice {
    private int id;
    private String name;
    private Double bid;
    private Double ask;
    private LocalDateTime timestamp;

    public void setMargins() {
        bid -= bid * (COMMISSION_PERCENT / 100);
        ask += ask * (COMMISSION_PERCENT / 100);
    }
}
