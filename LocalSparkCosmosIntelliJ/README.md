# Overview
This is a short guide to setting up IntelliJ IDE for local spark development and installing CosmosDB Spark connector to read and 
write to Cosmos DB. Thought, instructions are specific to Windows they will also work for Linux.

I personally like developing in a IDE as opposed to NoteBooks because IDE gives me the ability to open up spark source code quickly to review API definitions and implementations. This is especially useful when working with new APIs. 

# Getting started

## First:
* Install the latest version of the IntelliJ IDE
* Add Scala language plugin
* Download winutils.exe to a bin directory (ex: C:/winutils/bin) -> (for Windows OS only)
  * https://github.com/steveloughran/winutils
* Create a Cosmos DB collection for SQL API. Use "/deviceID" as the partition key

## Then:
* Import the SBT project "LocalSparkCosmosIntelliJ" into Intellij 
* Update the CosmosDB connection information in "HelloCosmos.scala" with appropriate values
* Make sure "hadoop.home.dir" system property is setup correctly in "HelloCosmos.scala" (ex: "C:\\winutils\\") -> (for Windows OS only)
* Run the main method in "HelloCosmos.scala" object. Documents will be first inserted and then read from the Cosmos DB collection  

## Notes:
* Review build.sbt file for the version information of Spark,Scala and Cosmos DB spark connector
* The log4j.properties file in the resources folder can to used to adjust logging behavior
