# Overview
This is a short guide to setting up IntelliJ IDE for local spark development and installing CosmosDB Spark connector to read and 
write to Cosmos DB. Instructions are specific to Windows but should also work for Linux 

# Getting started

## First:
* Install the latest version of the IntelliJ IDE
* Add Scala language plugin
* Download and setup winutils.exe  -> (for Windows OS only)
* Create a Cosmos DB collection for SQL API. Use "/deviceID" as the partition key

## Then:
* Import the SBT project "LocalSparkCosmosIntelliJ" into Intellij 
* Update the CosmosDB connection information in "HelloCosmos.scala" with appropriate values
* Make sure "hadoop.home.dir" system property is setup correctly in "HelloCosmos.scala"  -> (for Windows OS only)
* Run the main method in "HelloCosmos.scala" object. Documents will be first inserted and then read from the Cosmos DB collection  
