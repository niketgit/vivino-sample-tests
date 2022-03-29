package com.vivino.acceptance.pages;

import com.vivino.acceptance.driver.SharedDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WinePage {
    @Autowired
    SharedDriver driver;

    By wineName = By.xpath("//h1");
    By country = By.xpath("//*[@id=\"wine-location-header\"]/div[1]/div[1]/div[1]/div[1]/div[1]/span[1]/a[1]");
    By city = By.xpath("//*[@id=\"wine-location-header\"]/div[1]/div[1]/div[1]/div[1]/div[1]/span[2]/a[1]");
    By avgRating = By.xpath("//div[@class='_19ZcA']");
    By totalRatings = By.xpath("//div[@class='_1_k5A']");

    public String getWineName(){
        return driver.getDriver().findElement(wineName).getText();
    }

    public String getCountryName(){
        return driver.getDriver().findElement(country).getText();
    }

    public String getCity(){
        return driver.getDriver().findElement(city).getText();
    }

    public String getAvgRating(){
        return driver.getDriver().findElement(avgRating).getText();
    }

    public String getTotalRating(){
        return driver.getDriver().findElement(totalRatings).getText();
    }

    public By winePageHeader(){
        return wineName;
    }
}
