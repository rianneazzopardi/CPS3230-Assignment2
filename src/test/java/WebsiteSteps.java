import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WebsiteSteps {
    @Given("I am a user of the website")
    public void iAmAUserOfTheWebsite() {
    }

    @When("I visit the clothing website")
    public void iVisitTheClothingWebsite() {
    }

    @And("I click on the {string} category")
    public void iClickOnTheCategory(String arg0) {
    }

    @Then("I should be taken to {string} category")
    public void iShouldBeTakenToCategory(String arg0) {
    }

    @And("the category should show at least {int} products")
    public void theCategoryShouldShowAtLeastProducts(int arg0) {
    }

    @When("I click on the first product in the results")
    public void iClickOnTheFirstProductInTheResults() {
    }

    @Then("I should be taken to the details page for that product")
    public void iShouldBeTakenToTheDetailsPageForThatProduct() {
    }
}
