Feature: Make a call to the IVR Gateway Micro

  This feature tests to make sure a GET request to the IVR Gateway Micro returns the correct data.

  Scenario: A GET request is made to the endpoint
    Given An IVR-Gateway endpoint
    When A GET request is made to rampUpServer/fetch/googleUsers
    Then A List of JSON String data is produced to the kafka topic
    Then A List of JSON String data is consumed by the Kafka listener
    Then A List of JSON String data is returned to the caller