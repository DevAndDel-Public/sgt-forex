package com.devanddel.sgtforex.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Date: 14/6/21.
 * Author: Carlos Cuesta - Dev&Del
 *
 * Implements IETF devised RFC 7807 error-handling schema
 */
@Data
@Builder
public class ForexResult {
    public final String type;
    public final String title;
    public final int status;
    public final String detail;
    public final String instance;
    public Set<InstrumentPrice> result;
}
