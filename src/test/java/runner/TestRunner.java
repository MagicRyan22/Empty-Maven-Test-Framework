package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features" ,
        glue = "stepDef",
        dryRun = false,
        tags = "@Test",
        plugin = {"pretty"}
        //plugin = {"pretty","html:target/Cucumber.html","json:target/Cucumber.json", "rerun:target/failed.txt", "rerun:target/passed.txt"}
)
public class TestRunner {
}
