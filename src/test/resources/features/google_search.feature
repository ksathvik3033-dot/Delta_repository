Feature: Google Search

  Scenario: Search for Selenium WebDriver
    Given I open the Google homepage
    When I search for "Selenium WebDriver"
    Then I should see search results related to "Selenium WebDriver"

