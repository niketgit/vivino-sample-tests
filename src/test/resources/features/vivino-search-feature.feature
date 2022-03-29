@Search
Feature: Search Vivino Page

  Scenario Outline: Search Vivino page with Keyword '<Search Keyword>'
    Given user navigates to vivino website
    When user searches "<Search Keyword>" in search box
    And user collects all wines results from search results
    Then user verifies whether results contains searched keyword

    Examples:
      | Search Keyword |
      | Wine           |
      | Vino           |

  Scenario: Search Vivino page with Keyword '<Search Keyword>'
    Given user navigates to vivino website
    When user searches "Vino" in search box
    And user collects all wines results from search results
    And user clicks on any random wine from search
    Then user validates attributes matches with search results
