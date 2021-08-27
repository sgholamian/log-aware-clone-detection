//,temp,JsonFileMetricsReporter.java,111,128,temp,JsonReporter.java,109,125
//,3
public class xxx {
  @Override
  public void start() {
    // Create metrics directory if it is not present
    if (!metricsDir.toFile().exists()) {
      LOGGER.warn("Metrics directory {} does not exist, creating one", metricsDir);
      try {
        // createDirectories creates all non-existent parent directories
        Files.createDirectories(metricsDir, DIR_ATTRS);
      } catch (IOException e) {
        LOGGER.error("Failed to create directory {}: {}", metricsDir, e.getMessage());
        return;
      }
    }

    executorService = Executors.newScheduledThreadPool(1,
        new ThreadFactoryBuilder().setNameFormat(JSON_REPORTER_THREAD_NAME).build());
    executorService.scheduleWithFixedDelay(this,0, interval, TimeUnit.MILLISECONDS);
  }

};