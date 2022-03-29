package com.vivino.acceptance;

import com.vivino.acceptance.datamodel.SearchPageDataModel;
import com.vivino.acceptance.datamodel.WinePageDataModel;
import com.vivino.acceptance.steps.VivinoSearchPageSteps;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Hooks {
    @Autowired
    RuntimeData runtimeData;

    @Before
    public void before(Scenario scenario){
        VivinoSearchPageSteps.scenario = scenario;
    }

    @After
    public void after(Scenario scenario){
        runtimeData.setSearchText("");
        runtimeData.getSearchResults().clear();
        runtimeData.getSearchedData().clear();
        runtimeData.setSelectedWine(null);
        runtimeData.setClickedWine(null);
    }
}
