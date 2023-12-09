import io.cucumber.java.sl.In;
import io.github.sukgu.Shadow;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class Website {
    WebDriver driver;
    boolean foundCategory = false;
    List<WebElement> visibleProducts;
    String title = null;
    boolean addedToWishlist = false;
    boolean addedToCart = false;
    boolean searchComplete = false;
    boolean goneToCategory = false;

    public Website() {
        System.setProperty("webdriver.chrome.driver", "/Users/rianneazzopardi/Downloads/chromedriver-mac-x64 2/chromedriver");
        driver = new ChromeDriver();
    }

    public Website(boolean isModel) {
        System.setProperty("webdriver.chrome.driver", "/Users/rianneazzopardi/Downloads/chromedriver-mac-x64 2/chromedriver");
        driver = new ChromeDriver();
        navigateToWebsite();
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
        goneToCategory = true;
    }

    public void selectFirstProduct() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // because of UI of the website (in particular the side menu overlapping the first product), the website has to be full screen on 13" devices for this to work
        this.visibleProducts.get(0).click();
        // Getting the title to prove that the page actually loaded
        WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("titleProductCard")));
        title = titleElement.getText();

    }


    public void search(String arguments) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement search = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("menu-search")));
        WebElement searchButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("c-button")));
        Thread.sleep(5000);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", searchButton);
        Thread.sleep(5000);
        WebElement searchApp = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("search-app")));
        Thread.sleep(8000);
        searchApp.sendKeys(arguments);
        Thread.sleep(3000);
        searchApp.sendKeys(Keys.ENTER);
        searchComplete = true;
    }

    public void getProductsBySearch() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Shadow shadow = new Shadow(driver);
        WebElement section = shadow.findElement("search-app>search-grid>section");
        Thread.sleep(3000);
        WebElement results = section.findElement(By.cssSelector("section"));
        List<WebElement> gridProducts = results.findElements(By.cssSelector("grid-product"));
        this.visibleProducts = gridProducts;
    }

    public void selectingSize() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Shadow shadow = new Shadow(driver);
        Thread.sleep((2000));
        WebElement sizeSelector = shadow.findElement("size-selector-with-length>size-selector-select");
        sizeSelector.click();
        // because of animation
        Thread.sleep((2000));
        // catering for if not in stock
        if(sizeSelector.getText().contains("Notify")){
            List<WebElement> sizes = shadow.findElements("size-list>ul>li");
            WebElement sizeXS = sizes.get(0);
            WebElement sizeButton = sizeXS.findElement(By.className("size"));
            sizeButton.click();
            WebElement closeButton = shadow.findElement("main>button");
            closeButton.click();

            // because of animation
            Thread.sleep((2000));

            addToWishlist();
        }
        else if(sizeSelector.getText().contains("Similar")){
            List<WebElement> sizes = shadow.findElements("size-list>ul>li");
            WebElement sizeXS = sizes.get(0);
            WebElement sizeButton = sizeXS.findElement(By.className("size"));
            sizeButton.click();
            WebElement closeButton = shadow.findElement("main>button");
            closeButton.click();

            // because of animation
            Thread.sleep((2000));

            addToWishlist();
        }
        else{
            List<WebElement> sizes = shadow.findElements("size-list>ul>li");
            WebElement sizeXS = sizes.get(0);
            WebElement sizeButton = sizeXS.findElement(By.className("size"));
            sizeButton.click();
        }
    }

    public void addToCart() throws InterruptedException{
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement buttons =  wait.until(ExpectedConditions.presenceOfElementLocated(By.className("c-product-info--buttons-container")));
        WebElement addToBasket = buttons.findElement(By.tagName("button"));
        // because of animation
        Thread.sleep(2000);
        addToBasket.click();
        addedToCart = true;
    }

    public void addToWishlist() throws InterruptedException{
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement addToWishlist= wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("add-to-wishlist-button")));
        addToWishlist.click();
        addedToWishlist = true;
    }

    public void goToCart() throws InterruptedException{
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // because of animations
        Thread.sleep(5000);
        WebElement goToCartButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("buttonShopCart")));
        goToCartButton.click();
    }

    public void removeItemFromCart() throws InterruptedException{
        WebDriverWait wait = new WebDriverWait(driver, 10);
        Shadow shadow = new Shadow(driver);
        List<WebElement> cartList = driver.findElements(By.className("cart-list"));
        WebElement firstElement = shadow.findElement("div#cart-actions");
        WebElement remove = firstElement.findElement(By.className("deleteItem"));
        WebElement removeButton = remove.findElement(By.tagName("button"));
        removeButton.click();
        addedToCart = false;
        Thread.sleep(5000);
        WebElement closeCart = driver.findElement(By.id("close-shopcart"));
        closeCart.click();
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
