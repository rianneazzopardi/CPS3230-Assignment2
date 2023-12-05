import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.Locale;

public class WebsiteSteps {
    Website website;

    @Given("I am a user of the website")
    public void iAmAUserOfTheWebsite() {
        website = new Website();
    }

    @When("I visit the clothing website")
    public void iVisitTheClothingWebsite() {
        website.navigateToWebsite();
    }

    @And("I click on the {string} category")
    public void iClickOnTheCategory(String arg0) {
        website.navigateToCategory(arg0);
    }

    @Then("I should be taken to {string} category")
    public void iShouldBeTakenToCategory(String arg0) {
        Assertions.assertTrue(website.foundCategory);
    }

    @And("the category should show at least {int} products")
    public void theCategoryShouldShowAtLeastProducts(int arg0) {
        Assertions.assertTrue(website.foundProducts);
    }

    @When("I click on the first product in the results")
    public void iClickOnTheFirstProductInTheResults() {
        website.navigateToFirstProduct();
    }

    @Then("I should be taken to the details page for that product")
    public void iShouldBeTakenToTheDetailsPageForThatProduct() {
        String title = website.title;
        Assertions.assertNotNull(title);
    }
}
