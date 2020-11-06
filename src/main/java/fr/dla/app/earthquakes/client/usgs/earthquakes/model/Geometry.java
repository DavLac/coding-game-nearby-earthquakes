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
public class Geometry {
    private String type;
    private Float[] coordinates;
}
