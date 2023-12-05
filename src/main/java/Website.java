import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Website {
    WebDriver driver;
    boolean foundCategory = false;
    boolean foundProducts = false;
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
        // Locating and clicking on first category
        WebElement parentElement = driver.findElement(By.className("c-main-nav"));
//        WebElement childElement = parentElement.findElement(By.cssSelector("[data-component='nav-options']"));
        WebElement productCategoriesElement = parentElement.findElement(By.className("product-categories-and-trending"));
        WebElement categoriesElement = productCategoriesElement.findElement(By.className("product-categories"));
        WebElement anchorTag = productCategoriesElement.findElement(By.xpath(".//li[a/p/span[contains(text(),'" + category + "')]]/a"));
        if(anchorTag!=null){
            foundCategory = true;
        }
        anchorTag.click();
        //Locating and clicking on inner category if products can only be displayed when clicking the subcategory
        if(subcategory!=null) {
            WebElement subcategoriesContainer = categoriesElement.findElement(By.cssSelector("ul.subitems"));
            WebElement subcategoryLink = subcategoriesContainer.findElement(By.xpath(".//li[a/p/span[contains(text(),'" + subcategory + "')]]/a"));
            subcategoryLink.click();
        }

        if(anchorTag != null){
            foundProducts = true;
        }

    }

    public void navigateToFirstProduct() {
        WebElement productGrid = driver.findElement(By.className("m-tiles-box--product-grid"));
        WebElement productGridContainer = productGrid.findElement(By.id("ProductsByCategory"));
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Accept that the location is Malta
        WebElement saveStoreButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("saveStore")));
        saveStoreButton.click();

        WebElement gridGenerator = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("grid-generator")));

        // Locate the first legacy-product element within grid-generator
        WebElement firstLegacyProduct = gridGenerator.findElement(By.xpath(".//legacy-product[1]"));
        WebElement productTile = wait.until(ExpectedConditions.elementToBeClickable(By.className("c-tile--product")));
        WebElement tilesColumn = wait.until(ExpectedConditions.elementToBeClickable(By.className("carousel-item-container")));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", tilesColumn);

        // Getting the title to prove that the page actually loaded
        WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("titleProductCard")));
        title = titleElement.getText();
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
