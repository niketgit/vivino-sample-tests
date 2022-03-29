package com.vivino.acceptance;

import com.vivino.acceptance.datamodel.SearchPageDataModel;
import com.vivino.acceptance.datamodel.WinePageDataModel;
import lombok.Data;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class RuntimeData {
    private String searchText;
    private List<WebElement> searchResults;
    private List<SearchPageDataModel> searchedData;
    private SearchPageDataModel clickedWine;
    private WinePageDataModel selectedWine;

    public List<SearchPageDataModel> getSearchedData(){
        if(searchedData==null)
            searchedData = new ArrayList<>();
        return searchedData;
    }
}
