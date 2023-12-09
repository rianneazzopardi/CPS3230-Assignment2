import enums.States;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class WebsiteModelTest implements FsmModel {
    private Website systemUnderTest = new Website(true);

    private States modelWebsite = States.HOME;
    private boolean addedToWishList = false;
    private boolean addedToCart = false;
    private boolean searchComplete = false;
    private boolean goneToCategory = false;

    @Override
    public Object getState() {
        return modelWebsite;
    }

    @Override
    public void reset(boolean b) {
        if (b) {
            systemUnderTest = new Website(true);
        }
        modelWebsite = States.HOME;
        addedToWishList = false;
        addedToCart = false;
        searchComplete = false;
        goneToCategory = false;
    }

    public boolean goToCategoryGuard() {
        return getState().equals(States.HOME);
    }
    public @Action void goToCategory() throws InterruptedException {
        //Updating SUT
        systemUnderTest.navigateToCategory("Party");
        systemUnderTest.getProductsForCategory();

        //Updating model
        modelWebsite = States.CATEGORY;
        goneToCategory = true;

        //Checking correspondence between model and SUT.
        Assertions.assertEquals(goneToCategory, systemUnderTest.goneToCategory);
    }

    public boolean addToWishlistGuard() {
        return getState().equals(States.PRODUCT);
    }
    public @Action void addToWishlist() throws InterruptedException {
        //Updating SUT
        systemUnderTest.addToWishlist();

        //Updating model
        modelWebsite = States.WISHLIST;
        addedToWishList = true;

        //Checking correspondence between model and SUT.
        Assertions.assertEquals(addedToWishList, systemUnderTest.addedToWishlist);
    }

    public boolean searchGuard() {
        return getState().equals(States.HOME) || getState().equals(States.CATEGORY) || getState().equals(States.PRODUCT);
    }
    public @Action void search() throws InterruptedException {
        //Updating SUT
        systemUnderTest.search("Party");
        systemUnderTest.getProductsBySearch();

        //Updating model
        modelWebsite = States.SEARCH;
        searchComplete = true;

        //Checking correspondence between model and SUT.
        Assertions.assertEquals(searchComplete, systemUnderTest.searchComplete);
    }

    public boolean goToProductGuard() {
        return getState().equals(States.SEARCH) || getState().equals(States.CATEGORY);
    }
    public @Action void goToProduct() throws InterruptedException {
        //Updating SUT
        systemUnderTest.selectFirstProduct();

        //Updating model
        modelWebsite = States.PRODUCT;

        //Checking correspondence between model and SUT.
        Assertions.assertNotNull(systemUnderTest.title);
    }

    public boolean addToCartGuard() {
        return getState().equals(States.PRODUCT);
    }
    public @Action void addToCart() throws InterruptedException {
        //Updating SUT
        systemUnderTest.selectingSize();
        systemUnderTest.addToCart();
        systemUnderTest.goToCart();

        //Updating model
        modelWebsite = States.CART;
        addedToCart = true;

        //Checking correspondence between model and SUT.
        Assertions.assertEquals(addedToCart, systemUnderTest.addedToCart);
    }

    public boolean removedFromCartGuard() {
        return getState().equals(States.CART);
    }
    public @Action void removedFromCart() throws InterruptedException {
        //Updating SUT
        systemUnderTest.removeItemFromCart();

        //Updating model
        modelWebsite = States.CART;
        addedToCart = false;

        //Checking correspondence between model and SUT.
        Assertions.assertEquals(addedToCart, systemUnderTest.addedToCart);
    }

    @Test
    public void BulbOperatorModelRunner() {
        final GreedyTester tester = new GreedyTester(new WebsiteModelTest());
        tester.setRandom(new Random());
        tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(5);
        tester.printCoverage();
    }

}
