Feature: Amazon Sign In

  @amazonlogin
  Scenario: Click the Sign in button on Amazon homepage
    Given I open the Amazon homepage
    When I click the Sign in button
    Then I should see the Amazon sign in page


    @wirelessheadphones
  Scenario: Search for a product and open its details
    Given I open Amazon for product details
    When I search for "wireless headphones"
    And I click the first product
    Then I should see the product details page

