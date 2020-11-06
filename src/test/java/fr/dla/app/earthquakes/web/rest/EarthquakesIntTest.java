package fr.dla.app.earthquakes.web.rest;

import fr.dla.app.earthquakes.EarthquakesApp;
import fr.dla.app.earthquakes.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static fr.dla.app.earthquakes.service.CoordinatesEnum.PARIS_EIFFEL_TOWER;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.core.Every.everyItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {EarthquakesApp.class})
class EarthquakesIntTest {

    private static final String URI_EARTHQUAKES_NEARBY = "/api/earthquakes/nearby";
    private static final String PARAMETER_LATITUDE = "latitude";
    private static final String PARAMETER_LONGITUDE = "longitude";
    private static final String REGEX_OUTPUT_ROW = "^.{1,100} \\|\\| [0-9]{1,10}$";
    private static final int OUTPUT_MAX_SIZE = 10;

    @Autowired
    private EarthquakesResource controller;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Test
    void getNearbyEarthquakes_withGoodParameters_shouldReturnTenResults() throws Exception {
        mockMvc.perform(get(URI_EARTHQUAKES_NEARBY)
            .contentType(MediaType.APPLICATION_JSON)
            .param(PARAMETER_LATITUDE, PARIS_EIFFEL_TOWER.getLatitude().toString())
            .param(PARAMETER_LONGITUDE, PARIS_EIFFEL_TOWER.getLongitude().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(lessThanOrEqualTo(OUTPUT_MAX_SIZE))))
            .andExpect(jsonPath("$[*]").value(everyItem(matchesPattern(REGEX_OUTPUT_ROW))));
    }

    @Test
    void getNearbyEarthquakes_withBadLatitude_shouldReturnError() throws Exception {
        mockMvc.perform(get(URI_EARTHQUAKES_NEARBY)
            .contentType(MediaType.APPLICATION_JSON)
            .param(PARAMETER_LATITUDE, "null")
            .param(PARAMETER_LONGITUDE, PARIS_EIFFEL_TOWER.getLongitude().toString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getNearbyEarthquakes_withBadLongitude_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(URI_EARTHQUAKES_NEARBY)
            .contentType(MediaType.APPLICATION_JSON)
            .param(PARAMETER_LATITUDE, PARIS_EIFFEL_TOWER.getLatitude().toString())
            .param(PARAMETER_LONGITUDE, "181"))
            .andExpect(status().isBadRequest());
    }
}
