package com.vivino.acceptance.datamodel;

import lombok.Data;

@Data
public class SearchPageDataModel {
    private String wineName;
    private String wineCity;
    private String wineCountry;
    private String wineAvgRatings;
    private String wineTotalRatings;
}
