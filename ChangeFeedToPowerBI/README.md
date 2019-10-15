![](logo.png)

# Overview
Change feed support in Azure Cosmos DB works by listening to an Azure Cosmos container for any changes. The changes are persisted, can be processed asynchronously across one or more consumers. Further, these consumers can publish these changes to Power BI to update dashboards in real-time.  This sample demonstrates a simple end to end scenario of inserting telemetry into CosmosDB SQL API container, reading the change feed and publishing the changes to Power BI streaming endpoint. Azure Databricks is used  both write to and read from CosmosDB

# Getting started
## First:
* Create Azure Databricks cluster
* Install the latest azure-cosmosdb-spark library for the version of Apache Spark you are running
* Create a Cosmos DB collection for SQL API. Use "/deviceID" as the partition key
* Create real-time streaming dataset for power BI for the following message:
```
{
"deviceID" :"AAAAA555555",
"temperature" :98.6,
"pressure" :98.6,
"eventTime" :"AAAAA555555"
}
```
* Create a Power BI Tile of your choice to display the data 

## Then:
* Import "ToCosmos.scala" and "ToPowerBI.scala" into Databricks workspace
* Update the connection and configuration placeholders with appropriate values 
* Run "ToPowerBI.scala" notebook to get the change feed consumer running 
* Run "ToCosmos.scala" to insert telemetry into CosmoDB
* Power BI Tile gets updated in real-time with the telemetry from CosmosDB Change feed 
