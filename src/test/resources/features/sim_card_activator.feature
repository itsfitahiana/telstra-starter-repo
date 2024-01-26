Feature: SIM Card Activation

  Scenario: Successful Sim Card activation
    Given a functional sim card
    When I submit an activation request
    Then the activation is marked as true in the database

  Scenario: Failed Sim Card activation
    Given a broken sim card
    When I submit an activation request
    Then the activation is marked as false in the database