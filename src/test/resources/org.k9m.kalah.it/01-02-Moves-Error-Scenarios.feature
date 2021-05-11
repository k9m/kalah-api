Feature: Creating a new game

  Background:
    Given the system has started up
    Given no games are present

  Scenario: Trying to access a game that doesn't exist
    When trying to make a move for a game with id WRONG_ID that doesn't exist
    Then an error should be returned with message: Game with Id: WRONG_ID not found! and status code 404


  Scenario: Trying to make a move from an empty pit
    When a new game is created
    Then a response should be returned with a link to the new game
    When making a move from pit 1 from this game
    Then the state of the game should be ACTIVE
    Then the status of the game should be
      | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 |
      | 0 | 7 | 7 | 7 | 7 | 7 | 1 | 6 | 6 | 6  | 6  | 6  | 6  | 0  |
    When making a move from pit 1 from this game
    Then an error should be returned with message: There aren't any stones in pit: 1 and status code 400


  Scenario: Wrong player turn
    When a new game is created
    Then a response should be returned with a link to the new game
    When making a move from pit 2 from this game
    Then the state of the game should be ACTIVE
    Then the status of the game should be
      | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 |
      | 6 | 0 | 7 | 7 | 7 | 7 | 1 | 7 | 6 | 6  | 6  | 6  | 6  | 0  |
    When making a move from pit 3 from this game
    Then an error should be returned with message: Wrong player's turn, current player is: TWO and status code 400


  Scenario: Trying to make a move from a Kalah
    When a new game is created
    Then a response should be returned with a link to the new game
    When making a move from pit 7 from this game
    Then an error should be returned with message: This pit[7] is a Kalah, cannot make a move from here and status code 400


