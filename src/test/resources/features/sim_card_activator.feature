Feature: SIM Card Activation

  Background:
    Given the SIM Card Actuator is running

  Scenario: Successful Sim Card activation
    When I submit an activation request with ICCID "1255789453849037777" to the microservice
    Then the activation request is successfully processed
    When I submit a request to check the ID "1" activation status
    Then the activation is marked as true in the database

  Scenario: Failed Sim Card activation
    When I submit an activation request with ICCID "8944500102198304826" to the microservice
    Then the activation request is successfully processed
    When I submit a request to check the ID "1" activation status
    Then the activation is marked as false in the database