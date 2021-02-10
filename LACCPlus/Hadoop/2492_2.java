//,temp,KafkaSink.java,122,182,temp,TestKafkaMetrics.java,153,185
//,3
public class xxx {
  StringBuilder recordToJson(MetricsRecord record) {
    // Create a json object from a metrics record.
    StringBuilder jsonLines = new StringBuilder();
    Long timestamp = record.timestamp();
    Date currDate = new Date(timestamp);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String date = dateFormat.format(currDate);
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    String time = timeFormat.format(currDate);
    String hostname = new String("null");
    try {
      hostname = InetAddress.getLocalHost().getHostName();
    } catch (Exception e) {
      LOG.warn("Error getting Hostname, going to continue");
    }
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
    for (AbstractMetric m : record.metrics()) {
      jsonLines.append(
          ", \"" + m.name().toString().replaceAll("[\\p{Cc}]", "") + "\": ");
      jsonLines.append(" \"" + m.value().toString() + "\"");
    }
    jsonLines.append("}");
    return jsonLines;
  }

};