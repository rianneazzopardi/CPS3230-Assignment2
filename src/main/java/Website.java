import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Website {
    WebDriver driver;

    public Website() {
        System.setProperty("webdriver.chrome.driver", "/Users/rianneazzopardi/Downloads/chromedriver-mac-x64/chromedriver");
        driver = new ChromeDriver();
    }

    public void navigateToWebsite(){
        driver.get("https://www.pullandbear.com/mt/woman-n6417");
    }

    public void navigateToCategoryAndSubcategory(String category, String subcategory) {
        driver.get("https://www.pullandbear.com/mt/woman-n6417");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement cookiesPopUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
        cookiesPopUp.click();
        // Locating and clicking on first category
        WebElement parentElement = driver.findElement(By.className("c-main-nav"));
        WebElement childElement = parentElement.findElement(By.cssSelector("[data-component='nav-options']"));
        WebElement productCategoriesElement = childElement.findElement(By.className("product-categories-and-trending"));
        WebElement categoriesElement = productCategoriesElement.findElement(By.className("product-categories"));
        WebElement anchorTag = productCategoriesElement.findElement(By.xpath(".//li[a/p/span[contains(text(),'" + category + "')]]/a"));
        anchorTag.click();
        //Locating and clicking on inner category
        WebElement subcategoriesContainer = categoriesElement.findElement(By.cssSelector("ul.subitems"));
        WebElement subcategoryLink = subcategoriesContainer.findElement(By.xpath(".//li[a/p/span[contains(text(),'" + subcategory + "')]]/a"));
        subcategoryLink.click();
    }


}
