package com.devanddel.sgtforex.controller;

import com.devanddel.sgtforex.dao.ForexPricesDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.HttpHeaders.ACCEPT_ENCODING;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Date: 14/6/21.
 * Author: Carlos Cuesta - Dev&Del
 */
@SpringBootTest
@AutoConfigureMockMvc
//@RequiredArgsConstructor
class ForexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ForexPricesDAO forexPricesDAO;

    @BeforeEach
    void setUp() {
        forexPricesDAO.loadPrices("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001");
        forexPricesDAO.loadPrices("107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002");
        forexPricesDAO.loadPrices("108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002");
        forexPricesDAO.loadPrices("109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100");
        forexPricesDAO.loadPrices("110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110");
    }

    @Test
    void shouldGetAllPrices() throws Exception {
        this.mockMvc.perform(
                get("/api/getallprices").header(ACCEPT_ENCODING, APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.result").isArray())
                .andReturn();
    }

    @Test
    void shouldGetLatestPrice() throws Exception {
        this.mockMvc.perform(
                get("/api/getlatestprice")
                        .queryParam("id", "107")
                        .header(ACCEPT_ENCODING, APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.result").isArray())
                .andReturn();
    }

    @Test
    void shouldNotGetLatestPrice() throws Exception {
        this.mockMvc.perform(
                get("/api/getlatestprice")
                        .queryParam("id", "999")
                        .header(ACCEPT_ENCODING, APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.result").isEmpty())
                .andReturn();
    }
}