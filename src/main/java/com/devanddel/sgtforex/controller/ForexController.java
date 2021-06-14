package com.devanddel.sgtforex.controller;

import com.devanddel.sgtforex.domain.ForexResult;
import com.devanddel.sgtforex.domain.InstrumentPrice;
import com.devanddel.sgtforex.interfaces.ForexService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static com.devanddel.sgtforex.Constants.MSG_ERROR_PARAMETER_NULL;
import static java.util.Collections.emptySet;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Date: 14/6/21.
 * Author: Carlos Cuesta - Dev&Del
 */
@RequiredArgsConstructor
@RestController()
@RequestMapping("api")
public class ForexController {

    private final ForexService forexService;

    /**
     * @param request  (required) the HttpServletRequest
     * @return ResponseEntity with ForexResult including a InstrumentPrice set
     *      Implements IETF devised RFC 7807 error-handling schema
     */
    @GetMapping(path = {"/getallprices"}, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<ForexResult> getAllPrices(HttpServletRequest request) {
        Set<InstrumentPrice> result = forexService.getAllPrices();
        return ResponseEntity.ok(ForexResult.builder()
                .type("")
                .title(HttpStatus.OK.getReasonPhrase())
                .status(HttpStatus.OK.value())
                .detail("")
                .instance(request.getRequestURI())
                .result(result)
                .build());
    }

    /**
     * @param request  (required) the HttpServletRequest
     * @param id  (required) the instrument identification
     * @return ResponseEntity with ForexResult including a InstrumentPrice set with the instrument price
     *      Implements IETF devised RFC 7807 error-handling schema
     */
    @GetMapping(path = {"/getlatestprice"}, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<ForexResult> getLatestPrice(HttpServletRequest request, @RequestParam Integer id) {
        Set<InstrumentPrice> result;
        try {
            if (id == null){
                throw new IllegalArgumentException(MSG_ERROR_PARAMETER_NULL);
            }
            Optional<InstrumentPrice> latestPrice = forexService.getLatestPrice(id);
            result = latestPrice.map(Collections::singleton).orElse(emptySet());
        } catch (IllegalArgumentException iae) {
            return ResponseEntity
                    .badRequest()
                    .body(ForexResult.builder()
                            .type("/errors/bad-parameters")
                            .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .detail(iae.getMessage())
                            .instance(request.getRequestURI())
                            .result(emptySet())
                            .build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ForexResult.builder()
                            .type("/errors/server-error")
                            .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .detail(e.getMessage())
                            .instance(request.getRequestURI())
                            .result(emptySet())
                            .build());
        }
        return ResponseEntity.ok(ForexResult.builder()
                .type("")
                .title(HttpStatus.OK.getReasonPhrase())
                .status(HttpStatus.OK.value())
                .detail("")
                .instance(request.getRequestURI())
                .result(result)
                .build());
    }

}
