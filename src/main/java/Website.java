import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Website {
    WebDriver driver;
    boolean foundCategory = false;
    List<WebElement> visibleProducts;
    String title = null;

    public Website() {
        System.setProperty("webdriver.chrome.driver", "/Users/rianneazzopardi/Downloads/chromedriver-mac-x64/chromedriver");
        driver = new ChromeDriver();
    }

    public void navigateToWebsite(){
        WebDriverWait wait = new WebDriverWait(driver, 20);
        driver.get("https://www.pullandbear.com/mt/woman-n6417");
        wait.until(ExpectedConditions.titleContains("PULL&BEAR"));
        WebElement cookiesPopUp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onetrust-accept-btn-handler")));
        cookiesPopUp.click();
    }

    public void navigateToCategory(String category) {
        String subcategory = getSubCategories(category);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Locating and clicking on the first category
        WebElement anchorTag = driver.findElement(By.xpath(".//li[a/p/span[contains(text(),'" + category + "')]]/a"));

        if (anchorTag != null) {
            foundCategory = true;
            anchorTag.click();

            // Locating and clicking on the inner category if subcategory is not null
            if (subcategory != null) {
                WebElement subcategoryLink = anchorTag.findElement(By.xpath(".//ancestor::li/ul[contains(@class, 'subitems')]/li[a/p/span[contains(text(),'" + subcategory + "')]]/a"));
                subcategoryLink.click();
            }
        }

        // Accept that the location is Malta
        WebElement saveStoreButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("saveStore")));
        saveStoreButton.click();

    }

    public void getProductsForCategory() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement gridGenerator = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("grid-generator")));
        // Locate the first legacy-product element within grid-generator
        List<WebElement> legacyProducts = driver.findElements(By.cssSelector("legacy-product"));
        this.visibleProducts = legacyProducts;
        System.out.println(this.visibleProducts.size());
    }

    public void selectFirstProduct(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // because of UI of the website (in particular the side menu overlapping the first product), the website has to be full screen on 13" devices for this to work
        this.visibleProducts.get(0).click();
        // Getting the title to prove that the page actually loaded
        WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("titleProductCard")));
        title = titleElement.getText();

    }


    public void search(String arguments){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement search = driver.findElement(By.className("menu-search"));
        WebElement searchButton = search.findElement(By.className("c-button"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", searchButton);
        WebElement searchBar = driver.findElement(By.tagName("search-bar"));
        System.out.println();
    }



    public String getSubCategories(String category) {
        String subcategory = null;

        switch (category) {
            case "Clothing":
                subcategory = "Jackets";
                break;
            case "Shoes":
            case "Bags":
            case "Accessories":
                subcategory = "See All";
                break;
            case "Party":
                subcategory = null;
                break;
            default:
                subcategory = null;
        }

        return subcategory;
    }



}
