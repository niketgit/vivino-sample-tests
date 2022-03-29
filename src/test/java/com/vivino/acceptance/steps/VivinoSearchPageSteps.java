package com.vivino.acceptance.steps;

import com.vivino.acceptance.RuntimeData;
import com.vivino.acceptance.config.FrameworkProperties;
import com.vivino.acceptance.datamodel.SearchPageDataModel;
import com.vivino.acceptance.datamodel.WinePageDataModel;
import com.vivino.acceptance.driver.SharedDriver;
import com.vivino.acceptance.pages.SearchPage;
import com.vivino.acceptance.pages.WinePage;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

public class VivinoSearchPageSteps {

    @Autowired
    SharedDriver sharedDriver;

    @Autowired
    FrameworkProperties frameworkProperties;

    @Autowired
    RuntimeData runtimeData;

    @Autowired
    SearchPage searchPage;

    @Autowired
    WinePage winePage;

    public static Scenario scenario;

    @Given("user navigates to vivino website")
    public void openSearchPage() throws InterruptedException {
        sharedDriver.getDriver().get(frameworkProperties.getAppUrl());
        FluentWait wait = new FluentWait(sharedDriver.getDriver()).ignoring(NoSuchElementException.class)
                .pollingEvery(Duration.of(1, ChronoUnit.SECONDS)).withTimeout(Duration.of(60, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.presenceOfElementLocated(searchPage.searchBox()));
        //sharedDriver.getDriver().wait(5000);
    }

    @And("user searches {string} in search box")
    public void userSearches(String searchKeyword) {
        runtimeData.setSearchText(searchKeyword);
        searchPage.enterTextInSerachBox(searchKeyword);
        FluentWait wait = new FluentWait(sharedDriver.getDriver()).ignoring(NoSuchElementException.class)
                .pollingEvery(Duration.of(1, ChronoUnit.SECONDS)).withTimeout(Duration.of(60, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.presenceOfElementLocated(searchPage.searchResults()));
    }

    @And("user collects all wines results from search results")
    public void userCollects() {
        runtimeData.setSearchResults(searchPage.getSearchResults());
        runtimeData.getSearchResults().stream().forEach(wine -> {
            SearchPageDataModel searchPageDataModel = new SearchPageDataModel();
            searchPageDataModel.setWineName(searchPage.getWineName(wine));
            searchPageDataModel.setWineCity(searchPage.getWineCity(wine));
            searchPageDataModel.setWineCountry(searchPage.getWineCountry(wine));
            searchPageDataModel.setWineAvgRatings(searchPage.getWineAvgRating(wine));
            searchPageDataModel.setWineTotalRatings(searchPage.getWineTotalRatings(wine));
            runtimeData.getSearchedData().add(searchPageDataModel);
        });
        System.out.println("Total Search Results :" + runtimeData.getSearchedData().size());
    }

    @Then("user verifies whether results contains searched keyword")
    public void verifyResults() throws ClassNotFoundException {
        Class clazz = Class.forName("com.vivino.acceptance.datamodel.SearchPageDataModel");
        Field[] fields = clazz.getDeclaredFields();
        runtimeData.getSearchedData().stream().forEach(result -> {
            boolean textContains = false;
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    String fieldValue = String.valueOf(field.get(result));
                    if (fieldValue.toLowerCase().contains(runtimeData.getSearchText().toLowerCase())) {
                        System.out.println("Searched Wine with Name '" + result.getWineName() + "' contains keyword '" + runtimeData.getSearchText()+"' in '"+field.getName() +"'");
                        scenario.log("Searched Wine with Name '" + result.getWineName() + "' contains keyword '" + runtimeData.getSearchText()+"' in '"+field.getName() +"'");
                        textContains = true;
                        break;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if(!textContains) {
                System.out.println("Searched Wine with Name '" + result.getWineName() + "' doesn't contains keyword '" + runtimeData.getSearchText() + "' in any field.");
                scenario.log("Searched Wine with Name '" + result.getWineName() + "' doesn't contains keyword '" + runtimeData.getSearchText() + "' in any field.");
            }
        });
    }

    @When("user clicks on any random wine from search")
    public void userClickOnAnyRandomWine(){
        WebElement wine = runtimeData.getSearchResults().stream().findAny().get();
        FluentWait wait = new FluentWait(sharedDriver.getDriver()).ignoring(NoSuchElementException.class)
                .pollingEvery(Duration.of(1, ChronoUnit.SECONDS)).withTimeout(Duration.of(60, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.elementToBeClickable(wine));
        runtimeData.setClickedWine(runtimeData.getSearchedData().stream().filter(wines->wines.getWineName().equals(searchPage.getWineName(wine))).collect(Collectors.toList()).get(0));
        searchPage.clickWineNameLink(wine);
        scenario.log("Waiting for Wine Details......");
        wait.until(ExpectedConditions.presenceOfElementLocated(winePage.winePageHeader()));
        WinePageDataModel winePageDataModel = new WinePageDataModel();
        winePageDataModel.setWineCity(winePage.getCity());
        winePageDataModel.setWineAvgRatings(winePage.getAvgRating());
        winePageDataModel.setWineName(winePage.getWineName().replace("\n", " "));
        winePageDataModel.setWineCountry(winePage.getCountryName());
        winePageDataModel.setWineTotalRatings(winePage.getTotalRating());
        runtimeData.setSelectedWine(winePageDataModel);
    }

    @Then("user validates attributes matches with search results")
    public void matchWineAttributesWithSearch(){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(runtimeData.getSelectedWine().getWineName(), runtimeData.getClickedWine().getWineName(), "Wine Name Mismatch");
        softAssert.assertEquals(runtimeData.getSelectedWine().getWineCity(), runtimeData.getClickedWine().getWineCity(), "Wine City Mismatch");
        softAssert.assertEquals(runtimeData.getSelectedWine().getWineCountry(), runtimeData.getClickedWine().getWineCountry(), "Wine Country Mismatch");
        softAssert.assertEquals(runtimeData.getSelectedWine().getWineAvgRatings(), runtimeData.getClickedWine().getWineAvgRatings(), "Wine AVG Rating Mismatch");
        softAssert.assertEquals(runtimeData.getSelectedWine().getWineTotalRatings(), runtimeData.getClickedWine().getWineTotalRatings(), "Wine Total Rating Mismatch");
        softAssert.assertAll();
    }

}
