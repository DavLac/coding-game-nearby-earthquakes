package fr.dla.app.earthquakes.client.usgs.earthquakes.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Metadata {
    private Long generated;
    private String url;
    private String title;
    private String api;
    private Integer count;
    private Integer status;
}
