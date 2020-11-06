package fr.dla.app.earthquakes.service;

import fr.dla.app.earthquakes.client.usgs.earthquakes.UsgsEarthquakes;
import fr.dla.app.earthquakes.client.usgs.earthquakes.model.GeoJson;
import fr.dla.app.earthquakes.web.rest.errors.InternalServerErrorException;
import fr.dla.app.earthquakes.web.rest.errors.ProxyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static fr.dla.app.earthquakes.service.utils.CoordinatesUtils.distanceInKm;
import static fr.dla.app.earthquakes.service.utils.StreamUtils.distinctByKeys;

@Slf4j
@RequiredArgsConstructor
@Service
public class EarthquakesService {

    private static final String GEOJSON_ENTITY = "geojson";
    private static final String OUTPUT_SEPARATOR = " || ";
    private static final int OUTPUT_MAX_SIZE = 10;
    private static final String EMPTY_TITLE = "<Empty title>";

    private final UsgsEarthquakes usgsEarthquakes;

    //region public method
    public List<String> getNearbyEarthquakes(final Float latitude, final Float longitude) {
        final GeoJson geoJson = usgsEarthquakes.getAllMonthEarthquakes();

        handleGeoJsonResponse(geoJson);

        if (CollectionUtils.isEmpty(geoJson.getFeatures())) {
            return new ArrayList<>();
        }

        return usgsEarthquakes.getAllMonthEarthquakes().getFeatures().stream()
            .filter(feature -> feature.getGeometry() != null &&
                feature.getGeometry().getCoordinates().length >= 2) // keep only Features with coordinates
            .filter(distinctByKeys(
                feature -> feature.getGeometry().getCoordinates()[0],
                feature -> feature.getGeometry().getCoordinates()[1]
            )) // Filter duplicates coordinates
            .map(feature -> new ImmutablePair<String, Long>(
                    (feature.getProperties() != null) ? feature.getProperties().getTitle() : EMPTY_TITLE,
                    distanceInKm(
                        latitude,
                        longitude,
                        feature.getGeometry().getCoordinates()[1],
                        feature.getGeometry().getCoordinates()[0]
                    )
                )
            )  // Map to a Pair [Title + Distance between coordinates]
            .sorted(Comparator.comparingLong(Pair::getValue)) // Sort by distance
            .limit(OUTPUT_MAX_SIZE)
            .map(pair -> pair.getKey() + OUTPUT_SEPARATOR + pair.getValue()) // Format output
            .collect(Collectors.toList());
    }
    //end region public method

    //region private method
    private void handleGeoJsonResponse(GeoJson geoJson) {
        if (geoJson == null || geoJson.getMetadata() == null) {
            throw new InternalServerErrorException("Null GeoJson response", GEOJSON_ENTITY, "nullResponseError");
        }

        if (geoJson.getMetadata().getStatus() != HttpStatus.OK.value()) {
            throw new ProxyException("GeoJson response with an error", GEOJSON_ENTITY, "errorResponse");
        }
    }
    //end region private method
}
