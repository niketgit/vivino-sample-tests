package com.vivino.acceptance.driver;

import com.vivino.acceptance.config.FrameworkProperties;
import lombok.Data;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Data
@Component
public class SharedDriver{

    @Autowired
    FrameworkProperties frameworkProperties;

    private static WebDriver driver;
    private static EventFiringDecorator decorator;
    private static final Thread CLOSE_THREAD = new Thread(){
        @Override
        public void run(){
            try{
                driver.close();
            }finally {
                driver.quit();
            }
        }
    };



    static {
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    private static void createIEDriver(){
        try{
            Runtime.getRuntime().exec("taskkill /F /IM / IExplore.exe");
            Runtime.getRuntime().exec("taskkill /F /IM / IEDriverServer.exe");
        }catch (Exception e){

        }
        System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+File.separator+"src"+ File.separator+"test"
                +File.separator+"resources"+File.separator+"drivers"+File.separator+"IEDriverServer.exe");
        driver = new EventFiringDecorator().decorate(new InternetExplorerDriver());
    }

    private static void createChromeDriver(){
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+File.separator+"src"+ File.separator+"test"
                +File.separator+"resources"+File.separator+"drivers"+File.separator+"chromedriver.exe");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setAcceptInsecureCerts(true);
        driver = new EventFiringDecorator().decorate(new ChromeDriver(capabilities));
        driver.manage().window().maximize();
    }

    private static void createFFDriver(){

    }

    public WebDriver getDriver(){
        if(driver==null){
            switch (frameworkProperties.getBrowserName().toUpperCase()){
                case "CHROME" :
                    createChromeDriver();
                    break;
                case "IE" :
                    createIEDriver();
                    break;
                case "FF" :
                    createFFDriver();
                    break;
            }
        }
        return driver;
    }
}
