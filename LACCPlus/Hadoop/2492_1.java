//,temp,KafkaSink.java,122,182,temp,TestKafkaMetrics.java,153,185
//,3
public class xxx {
  @Override
  public void putMetrics(MetricsRecord record) {

    if (producer == null) {
      throw new MetricsException("Producer in KafkaSink is null!");
    }

    // Create the json object.
    StringBuilder jsonLines = new StringBuilder();

    long timestamp = record.timestamp();
    Instant instant = Instant.ofEpochMilli(timestamp);
    LocalDateTime ldt = LocalDateTime.ofInstant(instant, zoneId);
    String date = ldt.format(dateFormat);
    String time = ldt.format(timeFormat);

    // Collect datapoints and populate the json object.
    jsonLines.append("{\"hostname\": \"" + hostname);
    jsonLines.append("\", \"timestamp\": " + timestamp);
    jsonLines.append(", \"date\": \"" + date);
    jsonLines.append("\",\"time\": \"" + time);
    jsonLines.append("\",\"name\": \"" + record.name() + "\" ");
    for (MetricsTag tag : record.tags()) {
      jsonLines.append(
          ", \"" + tag.name().toString().replaceAll("[\\p{Cc}]", "") + "\": ");
      jsonLines.append(" \"" + tag.value().toString() + "\"");
    }
    for (AbstractMetric metric : record.metrics()) {
      jsonLines.append(", \""
          + metric.name().toString().replaceAll("[\\p{Cc}]", "") + "\": ");
      jsonLines.append(" \"" + metric.value().toString() + "\"");
    }
    jsonLines.append("}");
    LOG.debug("kafka message: " + jsonLines.toString());

    // Create the record to be sent from the json.
    ProducerRecord<Integer, byte[]> data = new ProducerRecord<Integer, byte[]>(
        topic, jsonLines.toString().getBytes(Charset.forName("UTF-8")));

    // Send the data to the Kafka broker. Here is an example of this data:
    // {"hostname": "...", "timestamp": 1436913651516,
    // "date": "2015-6-14","time": "22:40:51","context": "yarn","name":
    // "QueueMetrics, "running_0": "1", "running_60": "0", "running_300": "0",
    // "running_1440": "0", "AppsSubmitted": "1", "AppsRunning": "1",
    // "AppsPending": "0", "AppsCompleted": "0", "AppsKilled": "0",
    // "AppsFailed": "0", "AllocatedMB": "134656", "AllocatedVCores": "132",
    // "AllocatedContainers": "132", "AggregateContainersAllocated": "132",
    // "AggregateContainersReleased": "0", "AvailableMB": "0",
    // "AvailableVCores": "0", "PendingMB": "275456", "PendingVCores": "269",
    // "PendingContainers": "269", "ReservedMB": "0", "ReservedVCores": "0",
    // "ReservedContainers": "0", "ActiveUsers": "1", "ActiveApplications": "1"}
    Future<RecordMetadata> future = producer.send(data);
    jsonLines.setLength(0);
    try {
      future.get();
    } catch (InterruptedException e) {
      throw new MetricsException("Error sending data", e);
    } catch (ExecutionException e) {
      throw new MetricsException("Error sending data", e);
    }
  }

};