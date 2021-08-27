//,temp,RemoteSparkJobStatus.java,155,167,temp,RemoteSparkJobStatus.java,74,86
//,2
public class xxx {
  @Override
  public String getWebUIURL() {
    Future<String> getWebUIURL = sparkClient.run(new GetWebUIURLJob());
    try {
      return getWebUIURL.get(sparkClientTimeoutInSeconds, TimeUnit.SECONDS);
    } catch (Exception e) {
      LOG.warn("Failed to get web UI URL.", e);
      if (Thread.interrupted()) {
        error = e;
      }
      return "UNKNOWN";
    }
  }

};