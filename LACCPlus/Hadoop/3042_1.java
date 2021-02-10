//,temp,ResourceEstimatorServer.java,112,128,temp,ApplicationHistoryServer.java,164,184
//,3
public class xxx {
  static ResourceEstimatorServer startResourceEstimatorServer()
      throws IOException, InterruptedException {
    Configuration config = new YarnConfiguration();
    config.addResource(ResourceEstimatorConfiguration.CONFIG_FILE);
    ResourceEstimatorServer resourceEstimatorServer = null;
    try {
      resourceEstimatorServer = new ResourceEstimatorServer();
      ShutdownHookManager.get().addShutdownHook(
          new CompositeServiceShutdownHook(resourceEstimatorServer), 30);
      resourceEstimatorServer.init(config);
      resourceEstimatorServer.start();
    } catch (Throwable t) {
      LOGGER.error("Error starting ResourceEstimatorServer", t);
    }

    return resourceEstimatorServer;
  }

};