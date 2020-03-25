Feature: Creating a new game

  Background:
    Given the system has started up
    Given no games are present

  Scenario: Creating a new game and making moves
    When a new game is created
    Then a response should be returned with a link to the new game
    When making a move from pit 1 from this game
    Then the status of the game should be
      | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 |
      | 0 | 7 | 7 | 7 | 7 | 7 | 1 | 6 | 6 | 6  | 6  | 6  | 6  | 0  |
    When making a move from pit 2 from this game
    Then the status of the game should be
      | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 |
      | 0 | 0 | 8 | 8 | 8 | 8 | 2 | 7 | 7 | 6  | 6  | 6  | 6  | 0  |
    When making a move from pit 8 from this game
    Then the status of the game should be
      | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 |
      | 1 | 0 | 8 | 8 | 8 | 8 | 2 | 0 | 8 | 7  | 7  | 7  | 7  | 1  |


