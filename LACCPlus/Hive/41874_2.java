//,temp,JsonFileMetricsReporter.java,111,128,temp,JsonReporter.java,109,125
//,3
public class xxx {
  @Override
  public void start(long period, TimeUnit unit) {
    // Create metrics directory if it is not present
    if (!metricsDir.toFile().exists()) {
      LOG.warn("Metrics directory {} does not exist, creating one", metricsDir);
      try {
        // createDirectories creates all non-existent parent directories
        Files.createDirectories(metricsDir, DIR_ATTRS);
      } catch (IOException e) {
        LOG.warn("Failed to initialize JSON reporter: failed to create directory {}: {}", metricsDir, e.getMessage());
        return;
      }
    }
    jsonWriter = new ObjectMapper().registerModule(new MetricsModule(TimeUnit.MILLISECONDS,
        TimeUnit.MILLISECONDS, false)).writerWithDefaultPrettyPrinter();
    super.start(period, unit);
  }

};