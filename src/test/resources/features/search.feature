Feature: Search Functionality
  In order to help me with searching
  As a user of the website
  I want to be able to see the products


Scenario Outline: Search Functionality
  Given I am a user of the website
  When I search for a product using the term "<term>"
  Then I should see the search results
  And there should be at least 5 products in the search results
  When I click on the first product in the results
  Then I should be taken to the details page for that product

Examples:
  |term|
  |Party   |
  |Clothing  |
  |New  |
  |Online Exclusive |
  |Pacific Republic|
