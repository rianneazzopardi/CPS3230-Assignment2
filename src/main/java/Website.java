import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Website {
    WebDriver driver;

    public Website() {
        System.setProperty("webdriver.chome.driver", "/Users/rianneazzopardi/Downloads/chromedriver-mac-x64/chromedriver");
        driver = new ChromeDriver();
    }

}
