Feature: Reachability of product categories
  In order to help me with accessing categories
  As a user of the website
  I want to be able to access the categories

Scenario Outline: Reachability of product categories
Given I am a user of the website
When I visit the clothing website
And I click on the "<type>" category
Then I should be taken to "<type>" category
And the category should show at least 1 products
When I click on the first product in the results
Then I should be taken to the details page for that product

Examples:
|type|
|Party   |
|Clothing  |
|New  |
|Online Exclusive |
|Pacific Republic|
