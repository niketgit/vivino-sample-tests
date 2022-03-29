package com.vivino.acceptance.datamodel;

import lombok.Data;

@Data
public class WinePageDataModel {
    private String wineName;
    private String wineCity;
    private String wineCountry;
    private String wineAvgRatings;
    private String wineTotalRatings;
}
