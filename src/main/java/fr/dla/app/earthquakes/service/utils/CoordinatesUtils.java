package fr.dla.app.earthquakes.service.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CoordinatesUtils {
    /**
     * This routine calculates the distance between two points (given the
     * latitude/longitude of those points). It is being used to calculate
     * the distance between two locations using GeoDataSource (TM) products
     * <p>
     * Definitions:
     * Southern latitudes are negative, eastern longitudes are positive
     * <p>
     * Function parameters:
     * lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)
     * lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)
     * Worldwide cities and other features databases with latitude longitude
     * are available at https://www.geodatasource.com
     * <p>
     * For enquiries, please contact sales@geodatasource.com
     * <p>
     * Official Web site: https://www.geodatasource.com
     * <p>
     * GeoDataSource.com (C) All Rights Reserved 2019
     *
     * @param lat1 Latitude of point 1 (in decimal degrees)
     * @param lon1 Longitude of point 1 (in decimal degrees)
     * @param lat2 Latitude of point 2 (in decimal degrees)
     * @param lon2 Longitude of point 2 (in decimal degrees)
     * @return Distance in KM rounded
     */
    public static long distanceInKm(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }

        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344; // convert in KM
        return Math.round(dist);
    }
}
