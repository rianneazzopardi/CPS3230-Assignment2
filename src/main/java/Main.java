public class Main {
    public static void main(String[] args) throws InterruptedException {
        Website website = new Website();
        website.navigateToWebsite();
        website.navigateToCategory("Online Exclusive");
        website.getProductsForCategory();
        website.selectFirstProduct();

//        website.search("Party");
//        website.getProductsBySearch();
//        website.selectFirstProduct();
    }
}
