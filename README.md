# ATLAS-RAMP-UP-ANDREW-BENNETT

## Purpose

This service has four major components:
 * GatewayController calls a REST endpoint with Google user data
 * AssetsIngestService (kafka producer) sends the data to an assigned topic
 * AssetsIngestListener (kafka consumer) listens to the data stream
 * GatewayController returns the data to the application user

## Usage

Please note that for this service to run locally you need to also be running ivr-gateway-micro (propriety micro, sorry!).
Also, consult application.yml file to verify that the default ports are available in your environment. HMAC key and password are necessary and should be checked against current values before use (local.properties). Finally, Kafka Zookeeper and Server should be initialized before launching this service. At the time of this build the topic is integrated-data and the groupId is atlas-ramp-up, but this can be verified/modified in the KafkaConfig and AssetsIngestService files.



## Local Setup

Follow these steps to run this service on your local machine:
 * Launch Kafka Zookeeper and Kafka Server. Kafka Consumer can also be helpful to monitor the stream.
 * Run atlas-rampup-andrew-bennett and ivr-gateway-micro

