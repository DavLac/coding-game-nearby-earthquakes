package fr.dla.app.earthquakes.client.usgs.earthquakes;

import fr.dla.app.earthquakes.client.usgs.earthquakes.model.GeoJson;

public interface UsgsEarthquakes {
    GeoJson getAllMonthEarthquakes();
}
