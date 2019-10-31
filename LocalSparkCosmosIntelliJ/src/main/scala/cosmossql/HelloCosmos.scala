package cosmossql

import com.microsoft.azure.cosmosdb.spark.schema._
import com.microsoft.azure.cosmosdb.spark.CosmosDBSpark
import org.apache.spark.sql.functions._
import com.microsoft.azure.cosmosdb.spark.config.Config
import org.apache.spark.sql.{SaveMode, SparkSession}
import com.google.gson.Gson
import org.joda.time
import org.joda.time.format._
import java.sql.Timestamp

import scala.collection.mutable.ListBuffer
import scala.util.Random

object HelloCosmos {

  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\winutils\\\\")

    val spark = getSparkSession
    import spark.implicits._
    getTelemetry().toDF().write.mode(SaveMode.Overwrite).cosmosDB(getWriteConfig)
    val telemetryDF = spark.read.cosmosDB(getReadConfig).show
    System.exit(0)
  }

  case class Telemetry(deviceID: String, temperature: Integer, pressure: Integer, eventTime: String)

  def getTelemetry(): List[Telemetry] = {
    val recordsToSend = 200
    val deviceIDs = List("101", "102")
    val temperature = List(150, 170, 120, 220, 160, 130, 190, 175, 235, 185)
    val pressure = List(15, 17, 12, 22, 16, 13, 19, 17, 23, 18)
    var telemetry = new ListBuffer[Telemetry]()
    for (a <- 1 to recordsToSend) {
      val dateTime = new Timestamp(System.currentTimeMillis())
      telemetry += Telemetry(deviceIDs(Random.nextInt(deviceIDs.size)), temperature(Random.nextInt(temperature.size)), pressure(Random.nextInt(pressure.size)), dateTime.toString);
    }
    return telemetry.toList
  }

  private def getWriteConfig(): Config = {
    Config(Map(
      "Endpoint" -> "<>",
      "Masterkey" -> "<>",
      "Database" -> "samples",
      "Collection" -> "telemetry",
      "preferredRegions" -> "South Central US",
      "Upsert" -> "true"))
  }

  private def getReadConfig(): Config = {
    Config(Map(
      "Endpoint" -> "<>",
      "Masterkey" -> "<>",
      "Database" -> "samples",
      "Collection" -> "telemetry",
      "preferredRegions" -> "South Central US",
      "query_custom" -> "SELECT c.deviceID, c.pressure, c.temperature, c.eventTime FROM c"))
  }

  private def getSparkSession = {
    SparkSession
      .builder()
      .appName("test")
      .master("local[2]")
      .getOrCreate()
  }

}
