package fr.dla.app.earthquakes.client.usgs.earthquakes.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Property {
    private Float mag;
    private String place;
    private Long time;
    private Long updated;
    private Integer tz;
    private String url;
    private String detail;
    private Integer felt;
    private Float cdi;
    private Float mmi;
    private String alert;
    private String status;
    private Integer tsunami;
    private Integer sig;
    private String net;
    private String code;
    private String ids;
    private String sources;
    private String types;
    private Integer nst;
    private Float dmin;
    private Float rms;
    private Float gap;
    private String magType;
    private String type;
    private String title;
}
