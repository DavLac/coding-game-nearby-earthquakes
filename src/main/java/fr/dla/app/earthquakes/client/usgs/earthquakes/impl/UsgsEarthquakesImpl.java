package fr.dla.app.earthquakes.client.usgs.earthquakes.impl;

import fr.dla.app.earthquakes.client.usgs.earthquakes.UsgsEarthquakes;
import fr.dla.app.earthquakes.client.usgs.earthquakes.model.GeoJson;
import fr.dla.app.earthquakes.web.rest.errors.InternalServerErrorException;
import fr.dla.app.earthquakes.web.rest.errors.ProxyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class UsgsEarthquakesImpl implements UsgsEarthquakes {

    private static final String USGS_API_EXCEPTION_ERROR_KEY = "UsgsEarthquakesApiException";
    private static final String USGS_EARTHQUAKES_ENTITY = "usgs-earthquakes";

    private final RestTemplate restTemplate;

    @Value("${application.usgs-earthquakes-api.url}")
    private String endpointUrl;

    public UsgsEarthquakesImpl(@Qualifier("vanillaRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("getAllMonthEarthquakesCache")
    @Override
    public GeoJson getAllMonthEarthquakes() {

        log.info("Getting all month earthquakes");

        UriComponents requestBuilder = UriComponentsBuilder.fromHttpUrl(endpointUrl).build();

        log.info("GET request ---> {}", requestBuilder);

        ResponseEntity<GeoJson> responseEntity;

        try {
            responseEntity = restTemplate.getForEntity(requestBuilder.toString(), GeoJson.class);
        } catch (HttpClientErrorException ex) {
            throw new ProxyException(ex.getMessage(), USGS_EARTHQUAKES_ENTITY, USGS_API_EXCEPTION_ERROR_KEY);
        } catch (ResourceAccessException | HttpServerErrorException ex) {
            throw new InternalServerErrorException(ex.getMessage(), USGS_EARTHQUAKES_ENTITY, USGS_API_EXCEPTION_ERROR_KEY);
        }

        log.info("GET response <--- {}", responseEntity);

        return responseEntity.getBody();
    }
}
