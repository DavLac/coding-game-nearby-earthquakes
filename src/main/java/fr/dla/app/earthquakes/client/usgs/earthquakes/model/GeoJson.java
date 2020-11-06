package fr.dla.app.earthquakes.client.usgs.earthquakes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoJson {
    private String type;
    private Metadata metadata;
    private List<Feature> features;
    private Float[] bbox;

    @Override
    public String toString() {
        String featureToString = (features != null) ? String.valueOf(features.size()) : "null";
        return "GeoJson{" +
            "type='" + type + '\'' +
            ", metadata=" + metadata +
            ", features size=" + featureToString +
            ", bbox=" + Arrays.toString(bbox) +
            '}';
    }
}
