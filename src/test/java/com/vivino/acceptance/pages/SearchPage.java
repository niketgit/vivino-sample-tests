package com.vivino.acceptance.pages;

import com.vivino.acceptance.driver.SharedDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchPage {

    @Autowired
    SharedDriver driver;

    By searchKeywordTab = By.xpath("//input[@name='q']");
    By searchResults = By.cssSelector("div[class='card card-lg'");
    By wineName = By.xpath("(.//div[@class='wine-card__header-wrapper']/span)[1]");
    By wineNameLink = By.xpath(".//a[@class='link-color-alt-grey']");
    By wineCity = By.xpath("(.//div[@class='wine-card__header-wrapper']/span)[2]/a[1]");;
    By wineCountry = By.xpath("(.//div[@class='wine-card__header-wrapper']/span)[2]/a[2]");;
    By wineAvgRatings = By.xpath("(.//div[@class='average__container'])[1]/div[1]");;
    By wineTotalRatings = By.xpath("(.//div[@class='average__container'])[1]/div[2]");

    public void enterTextInSerachBox(String keyWordToSearch){
        driver.getDriver().findElement(searchKeywordTab).sendKeys(keyWordToSearch);
        driver.getDriver().findElement(searchKeywordTab).sendKeys(Keys.ENTER);
    }

    public List<WebElement> getSearchResults(){
        return driver.getDriver().findElements(searchResults);
    }

    public String getWineName(WebElement wine){
        return wine.findElement(wineName).getText();
    }

    public String getWineCity(WebElement wine){
        return wine.findElement(wineCity).getText();
    }

    public String getWineCountry(WebElement wine){
        return wine.findElement(wineCountry).getText();
    }

    public String getWineAvgRating(WebElement wine){
        return wine.findElement(wineAvgRatings).getText();
    }

    public String getWineTotalRatings(WebElement wine){
        return wine.findElement(wineTotalRatings).getText();
    }

    public By searchBox(){
        return searchKeywordTab;
    }

    public By searchResults(){
        return searchResults;
    }

    public void clickWineNameLink(WebElement wine){
        wine.findElement(wineNameLink).click();
    }
}
