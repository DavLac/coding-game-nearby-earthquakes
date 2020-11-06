package fr.dla.app.earthquakes.service;

import fr.dla.app.earthquakes.client.usgs.earthquakes.UsgsEarthquakes;
import fr.dla.app.earthquakes.client.usgs.earthquakes.model.Feature;
import fr.dla.app.earthquakes.client.usgs.earthquakes.model.GeoJson;
import fr.dla.app.earthquakes.client.usgs.earthquakes.model.Metadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static fr.dla.app.earthquakes.service.CoordinatesEnum.DISNEYLAND_PARIS;
import static fr.dla.app.earthquakes.service.CoordinatesEnum.MALDIVES_ISLAND;
import static fr.dla.app.earthquakes.service.CoordinatesEnum.PARIS_EIFFEL_TOWER;
import static fr.dla.app.earthquakes.service.CoordinatesEnum.POINT_1;
import static fr.dla.app.earthquakes.service.CoordinatesEnum.POINT_2;
import static fr.dla.app.earthquakes.service.CoordinatesEnum.POINT_3;
import static fr.dla.app.earthquakes.service.CoordinatesEnum.POINT_4;
import static fr.dla.app.earthquakes.service.CoordinatesEnum.POINT_5;
import static fr.dla.app.earthquakes.service.CoordinatesEnum.POINT_6;
import static fr.dla.app.earthquakes.service.CoordinatesEnum.POINT_7;
import static fr.dla.app.earthquakes.service.CoordinatesEnum.SMARTRECRUITER_BERLIN_OFFICE;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class EarthquakesServiceTest {

    private static final String SEPARATOR = " \\|\\| ";
    private static final String EMPTY_TITLE = "<Empty title>";

    @InjectMocks
    private EarthquakesService earthquakesService;

    @Mock
    private UsgsEarthquakes usgsEarthquakes;

    @Test
    void getNearbyEarthquakes_withGoodParameters_shouldReturnNearbyEarthquakes() {
        //inputs
        Metadata metadata = new Metadata();
        metadata.setStatus(HttpStatus.OK.value());
        GeoJson geoJson = GeoJson.builder()
            .features(Arrays.asList(
                new Feature(DISNEYLAND_PARIS.getTitle(), DISNEYLAND_PARIS.getLatitude(), DISNEYLAND_PARIS.getLongitude()),
                new Feature(SMARTRECRUITER_BERLIN_OFFICE.getTitle(), SMARTRECRUITER_BERLIN_OFFICE.getLatitude(), SMARTRECRUITER_BERLIN_OFFICE.getLongitude()),
                new Feature(MALDIVES_ISLAND.getTitle(), MALDIVES_ISLAND.getLatitude(), MALDIVES_ISLAND.getLongitude())
            ))
            .metadata(metadata)
            .build();
        Mockito.when(usgsEarthquakes.getAllMonthEarthquakes()).thenReturn(geoJson);

        //test
        List<String> earthquakesResponse = earthquakesService.getNearbyEarthquakes(PARIS_EIFFEL_TOWER.getLatitude(),
            PARIS_EIFFEL_TOWER.getLongitude());

        assertThat(earthquakesResponse.size()).isEqualTo(3);
        assertThat(earthquakesResponse.get(0).split(SEPARATOR)[0]).isEqualTo(DISNEYLAND_PARIS.getTitle());
        assertThat(earthquakesResponse.get(0).split(SEPARATOR)[1]).isEqualTo("36");
        assertThat(earthquakesResponse.get(1).split(SEPARATOR)[0]).isEqualTo(SMARTRECRUITER_BERLIN_OFFICE.getTitle());
        assertThat(earthquakesResponse.get(1).split(SEPARATOR)[1]).isEqualTo("879");
        assertThat(earthquakesResponse.get(2).split(SEPARATOR)[0]).isEqualTo(MALDIVES_ISLAND.getTitle());
        assertThat(earthquakesResponse.get(2).split(SEPARATOR)[1]).isEqualTo("8670");
    }

    @Test
    void getNearbyEarthquakes_withDuplicateCoordinates_shouldReturnNearbyEarthquakesDistinct() {
        //inputs
        Metadata metadata = new Metadata();
        metadata.setStatus(HttpStatus.OK.value());
        GeoJson geoJson = GeoJson.builder()
            .features(Arrays.asList(
                new Feature(SMARTRECRUITER_BERLIN_OFFICE.getTitle(), SMARTRECRUITER_BERLIN_OFFICE.getLatitude(), SMARTRECRUITER_BERLIN_OFFICE.getLongitude()),
                new Feature(SMARTRECRUITER_BERLIN_OFFICE.getTitle(), SMARTRECRUITER_BERLIN_OFFICE.getLatitude(), SMARTRECRUITER_BERLIN_OFFICE.getLongitude()),
                new Feature(MALDIVES_ISLAND.getTitle(), MALDIVES_ISLAND.getLatitude(), MALDIVES_ISLAND.getLongitude()),
                new Feature(MALDIVES_ISLAND.getTitle(), MALDIVES_ISLAND.getLatitude(), MALDIVES_ISLAND.getLongitude())

            ))
            .metadata(metadata)
            .build();
        Mockito.when(usgsEarthquakes.getAllMonthEarthquakes()).thenReturn(geoJson);

        //test
        List<String> earthquakesResponse = earthquakesService.getNearbyEarthquakes(PARIS_EIFFEL_TOWER.getLatitude(),
            PARIS_EIFFEL_TOWER.getLongitude());

        assertThat(earthquakesResponse.size()).isEqualTo(2);
        assertThat(earthquakesResponse.get(0).split(SEPARATOR)[0]).isEqualTo(SMARTRECRUITER_BERLIN_OFFICE.getTitle());
        assertThat(earthquakesResponse.get(1).split(SEPARATOR)[0]).isEqualTo(MALDIVES_ISLAND.getTitle());
    }

    @Test
    void getNearbyEarthquakes_withGoodParameters_shouldReturnNearbyEarthquakesSorted() {
        //inputs
        Metadata metadata = new Metadata();
        metadata.setStatus(HttpStatus.OK.value());
        GeoJson geoJson = GeoJson.builder()
            .features(Arrays.asList(
                new Feature(SMARTRECRUITER_BERLIN_OFFICE.getTitle(), SMARTRECRUITER_BERLIN_OFFICE.getLatitude(), SMARTRECRUITER_BERLIN_OFFICE.getLongitude()),
                new Feature(MALDIVES_ISLAND.getTitle(), MALDIVES_ISLAND.getLatitude(), MALDIVES_ISLAND.getLongitude()),
                new Feature(DISNEYLAND_PARIS.getTitle(), DISNEYLAND_PARIS.getLatitude(), DISNEYLAND_PARIS.getLongitude())
            ))
            .metadata(metadata)
            .build();
        Mockito.when(usgsEarthquakes.getAllMonthEarthquakes()).thenReturn(geoJson);

        //test
        List<String> earthquakesResponse = earthquakesService.getNearbyEarthquakes(PARIS_EIFFEL_TOWER.getLatitude(),
            PARIS_EIFFEL_TOWER.getLongitude());

        assertThat(earthquakesResponse.size()).isEqualTo(3);
        assertThat(earthquakesResponse.get(0).split(SEPARATOR)[0]).isEqualTo(DISNEYLAND_PARIS.getTitle());
        assertThat(earthquakesResponse.get(1).split(SEPARATOR)[0]).isEqualTo(SMARTRECRUITER_BERLIN_OFFICE.getTitle());
        assertThat(earthquakesResponse.get(2).split(SEPARATOR)[0]).isEqualTo(MALDIVES_ISLAND.getTitle());
    }

    @Test
    void getNearbyEarthquakes_withFeaturePropertyNull_shouldReturnNearbyEarthquakesEmptyTitle() {
        //inputs
        Metadata metadata = new Metadata();
        metadata.setStatus(HttpStatus.OK.value());

        Feature feature = new Feature(null, DISNEYLAND_PARIS.getLatitude(), DISNEYLAND_PARIS.getLongitude());
        feature.setProperties(null);

        GeoJson geoJson = GeoJson.builder()
            .features(Collections.singletonList(feature))
            .metadata(metadata)
            .build();
        Mockito.when(usgsEarthquakes.getAllMonthEarthquakes()).thenReturn(geoJson);

        //test
        List<String> earthquakesResponse = earthquakesService.getNearbyEarthquakes(PARIS_EIFFEL_TOWER.getLatitude(),
            PARIS_EIFFEL_TOWER.getLongitude());

        assertThat(earthquakesResponse.size()).isEqualTo(1);
        assertThat(earthquakesResponse.get(0).split(SEPARATOR)[0]).isEqualTo(EMPTY_TITLE);
    }

    @Test
    void getNearbyEarthquakes_withTooMuchResponse_shouldReturnTenNearbyEarthquakes() {
        //inputs
        Metadata metadata = new Metadata();
        metadata.setStatus(HttpStatus.OK.value());
        GeoJson geoJson = GeoJson.builder()
            .features(Arrays.asList(
                new Feature(SMARTRECRUITER_BERLIN_OFFICE.getTitle(), SMARTRECRUITER_BERLIN_OFFICE.getLatitude(), SMARTRECRUITER_BERLIN_OFFICE.getLongitude()),
                new Feature(MALDIVES_ISLAND.getTitle(), MALDIVES_ISLAND.getLatitude(), MALDIVES_ISLAND.getLongitude()),
                new Feature(DISNEYLAND_PARIS.getTitle(), DISNEYLAND_PARIS.getLatitude(), DISNEYLAND_PARIS.getLongitude()),
                new Feature(POINT_1.getTitle(), POINT_1.getLatitude(), POINT_1.getLongitude()),
                new Feature(POINT_2.getTitle(), POINT_2.getLatitude(), POINT_2.getLongitude()),
                new Feature(POINT_3.getTitle(), POINT_3.getLatitude(), POINT_3.getLongitude()),
                new Feature(POINT_4.getTitle(), POINT_4.getLatitude(), POINT_4.getLongitude()),
                new Feature(POINT_5.getTitle(), POINT_5.getLatitude(), POINT_5.getLongitude()),
                new Feature(POINT_6.getTitle(), POINT_6.getLatitude(), POINT_6.getLongitude()),
                new Feature(POINT_7.getTitle(), POINT_7.getLatitude(), POINT_7.getLongitude()),
                new Feature(PARIS_EIFFEL_TOWER.getTitle(), PARIS_EIFFEL_TOWER.getLatitude(), PARIS_EIFFEL_TOWER.getLongitude())
            ))
            .metadata(metadata)
            .build();
        Mockito.when(usgsEarthquakes.getAllMonthEarthquakes()).thenReturn(geoJson);

        //test
        List<String> earthquakesResponse = earthquakesService.getNearbyEarthquakes(PARIS_EIFFEL_TOWER.getLatitude(),
            PARIS_EIFFEL_TOWER.getLongitude());

        assertThat(earthquakesResponse.size()).isEqualTo(10);
    }

    @Test
    void getNearbyEarthquakes_withNullFeatureResponse_shouldReturnEmptyList() {
        //inputs
        Metadata metadata = new Metadata();
        metadata.setStatus(HttpStatus.OK.value());
        GeoJson geoJson = GeoJson.builder()
            .features(null)
            .metadata(metadata)
            .build();
        Mockito.when(usgsEarthquakes.getAllMonthEarthquakes()).thenReturn(geoJson);

        //test
        List<String> earthquakesResponse = earthquakesService.getNearbyEarthquakes(PARIS_EIFFEL_TOWER.getLatitude(),
            PARIS_EIFFEL_TOWER.getLongitude());

        assertThat(earthquakesResponse.size()).isEqualTo(0);
    }
}
