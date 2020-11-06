package fr.dla.app.earthquakes.client.usgs.earthquakes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Feature {
    private String type;
    private Property properties;
    private Geometry geometry;
    private String id;

    public Feature(String title, Float latitude, Float longitude) {
        this.geometry = new Geometry(null, new Float[]{longitude, latitude});
        Property property = new Property();
        property.setTitle(title);
        this.properties = property;
    }
}
