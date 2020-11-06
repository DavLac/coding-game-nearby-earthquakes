package fr.dla.app.earthquakes.web.rest;

import fr.dla.app.earthquakes.service.EarthquakesService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/api/earthquakes")
public class EarthquakesResource {

    private final EarthquakesService earthquakesService;

    /**
     * List 10 earthquakes that happened in the closest proximity to input point, in the order
     * from the closest to the furthest
     * <p>
     * Input point = latitude + longitude
     *
     * @param latitude  Input point latitude
     * @param longitude Input point longitude
     * @return Earthquakes list
     */
    @GetMapping("/nearby")
    @ApiOperation("Get nearby earthquakes")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<String>> getNearbyEarthquakes(
        @ApiParam("Input point latitude") @RequestParam
        @NotNull
        @Digits(integer = 2, fraction = 6)
        @Max(90)
        @Min(-90)
            float latitude,
        @ApiParam("Input point longitude") @RequestParam
        @NotNull
        @Digits(integer = 3, fraction = 6)
        @Max(180)
        @Min(-180)
            float longitude
    ) {
        log.info("GET request to get nearby earthquakes list. latitude = {}, longitude = {}", latitude, longitude);
        return ResponseEntity.ok(earthquakesService.getNearbyEarthquakes(latitude, longitude));
    }
}
