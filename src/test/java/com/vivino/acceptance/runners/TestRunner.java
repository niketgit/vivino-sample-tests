package com.vivino.acceptance.runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.vivino.acceptance"},
        tags = "@Search",
        plugin = {"json:target/cucumber-reports/CucumberTestReport.json","pretty", "html:target/cucumber-reports/cucumber-pretty","json:target/cucumber-reports/CucumberTestReport.json","rerun:target/cucumber-reports/rerun.txt"},
        monochrome=true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    public TestRunner(){

    }
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
