package fr.dla.app.earthquakes.service;

import lombok.Getter;

@Getter
public enum CoordinatesEnum {
    PARIS_EIFFEL_TOWER("Paris Eiffel Tower", 48.858245F, 2.294642F),
    DISNEYLAND_PARIS("DisneyLand Paris", 48.868480F, 2.781909F),
    SMARTRECRUITER_BERLIN_OFFICE("SmartRecruiter Berlin Office", 52.503651F, 13.386108F),
    MALDIVES_ISLAND("Maldives Island", -0.611764F, 73.093789F),
    POINT_1("Point 1", 1F, 1F),
    POINT_2("Point 2", 2F, 1F),
    POINT_3("Point 3", 3F, 1F),
    POINT_4("Point 4", 4F, 1F),
    POINT_5("Point 5", 5F, 1F),
    POINT_6("Point 6", 6F, 1F),
    POINT_7("Point 7", 7F, 1F);

    private String title;
    private Float latitude;
    private Float longitude;

    CoordinatesEnum(String title, Float latitude, Float longitude) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
