## Card Payments Streams
The project contains a potential solution for streaming credit card transactions and authorization reponses and producing daily and monthly aggregated metrics for it.

---

## Problem
We have credit card transaction data flowing in from a source. We have another credit card payment gateway event which gives the information if the transaction actually went through.
i.e; the payment gateway would give you the information for each transaction id, if the actual payment was successful, declined or undefined.
The delay to get the event from the credit card payment gateway could range from 0 seconds to 5 hours.
For each successful and declined transaction, we need to send a notification to the customer with the payment status.
We need to generate a daily report with the total number of successful transactions and the total number of declines transactions for a card We need to get a monthly report on the percentage of successful transactions and declined transactions
We also need the average monthly spend (Consider only the successful transactions) using the credit card in the monthly report
Implement the generation of daily and monthly reports.

---
Table of Contents

* [Requirements](#requirements)
* [Packaging and running](#packaging-and-running)
* [Parking Lot](#parking-lot)
* [Improvements](#improvements)

---

<a name="requirements"/>

### Requirements
* You need to have docker installed to run the program using docker-compose
* You can run the program locally without docker by installing kafka binaries

<a name="packaging-and-running"/>

### Packaging and running
* You need to run sbt assembly to create the assembly JAR
* Next, you can run docker-compose up -d which will run all services in an ordered manner

<a name="parking-lot"/>

### Parking Lot
* Unit testing streams logic
* Using Kafka Connect to sink out the aggregate metrics to an external storage
* Add a REST API to read the metrics values
* Use a config library (e.g. pureconfig) to specify windows, timeouts etc
* Add logging
* Fix docker compose setup
* Use of EnumEntry instead of strings

<a name="improvements"/>

### Improvements
* Use confluent kafka
* Use schema registry
* Use avro format
