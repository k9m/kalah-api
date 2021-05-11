Feature: Viewing game history

  Background:
    Given the system has started up
    Given no games are present

  Scenario: Creating a new game and making moves and then checking the history of the moves
    When a new game is created
    Then a response should be returned with an id generated
    When making a move from pit 1 from this game
    Then the state of the game should be ACTIVE and player turn ONE
    When checked via the API, the state of the game should be ACTIVE
    Then the status of the game should be
      | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 |
      | 0 | 7 | 7 | 7 | 7 | 7 | 1 | 6 | 6 | 6  | 6  | 6  | 6  | 0  |

    When making a move from pit 6 from this game
    Then the state of the game should be ACTIVE and player turn TWO
    When checked via the API, the state of the game should be ACTIVE
    Then the status of the game should be
      | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 |
      | 0 | 7 | 7 | 7 | 7 | 0 | 2 | 7 | 7 | 7  | 7  | 7  | 7  | 0  |

    When making a move from pit 13 from this game
    Then the state of the game should be ACTIVE and player turn ONE
    When checked via the API, the state of the game should be ACTIVE
    Then the status of the game should be
      | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 |
      | 1 | 8 | 8 | 8 | 8 | 0 | 2 | 0 | 7 | 7  | 7  | 7  | 0  | 9  |

    When making a move from pit 5 from this game
    Then the state of the game should be ACTIVE and player turn TWO
    When checked via the API, the state of the game should be ACTIVE
    Then the status of the game should be
      | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 |
      | 0 | 8 | 8 | 8 | 0 | 1 | 5 | 1 | 8 | 8  | 8  | 8  | 0  | 9  |

    Then checking the history for this game should display the following
      | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 |
      | 6 | 6 | 6 | 6 | 6 | 6 | 0 | 6 | 6 | 6  | 6  | 6  | 6  | 0  |
      | 0 | 7 | 7 | 7 | 7 | 7 | 1 | 6 | 6 | 6  | 6  | 6  | 6  | 0  |
      | 0 | 7 | 7 | 7 | 7 | 0 | 2 | 7 | 7 | 7  | 7  | 7  | 7  | 0  |
      | 1 | 8 | 8 | 8 | 8 | 0 | 2 | 0 | 7 | 7  | 7  | 7  | 0  | 9  |
      | 0 | 8 | 8 | 8 | 0 | 1 | 5 | 1 | 8 | 8  | 8  | 8  | 0  | 9  |


