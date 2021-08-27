//,temp,RemoteSparkJobStatus.java,155,167,temp,RemoteSparkJobStatus.java,74,86
//,2
public class xxx {
  @Override
  public String getAppID() {
    Future<String> getAppID = sparkClient.run(new GetAppIDJob());
    try {
      return getAppID.get(sparkClientTimeoutInSeconds, TimeUnit.SECONDS);
    } catch (Exception e) {
      LOG.warn("Failed to get APP ID.", e);
      if (Thread.interrupted()) {
        error = e;
      }
      return null;
    }
  }

};