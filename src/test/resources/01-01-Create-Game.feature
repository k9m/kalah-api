Feature: Creating a new game

  Background:
    Given the system has started up
    Given the database is empty


  Scenario: Creating a new game
    When a new game is created
    Then a response should be returned with a link to the new game


