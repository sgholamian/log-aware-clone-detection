//,temp,MiniYARNCluster.java,709,737,temp,TestResourceManagerAdministrationProtocolPBClientImpl.java,70,102
//,3
public class xxx {
  @BeforeClass
  public static void setUpResourceManager() throws IOException,
          InterruptedException {
    Configuration.addDefaultResource("config-with-security.xml");
    Configuration configuration = new YarnConfiguration();
    resourceManager = new ResourceManager() {
      @Override
      protected void doSecureLogin() throws IOException {
      }
    };
    resourceManager.init(configuration);
    new Thread() {
      public void run() {
        resourceManager.start();
      }
    }.start();
    int waitCount = 0;
    while (resourceManager.getServiceState() == STATE.INITED
            && waitCount++ < 10) {
      LOG.info("Waiting for RM to start...");
      Thread.sleep(1000);
    }
    if (resourceManager.getServiceState() != STATE.STARTED) {
      throw new IOException("ResourceManager failed to start. Final state is "
              + resourceManager.getServiceState());
    }
    LOG.info("ResourceManager RMAdmin address: "
            + configuration.get(YarnConfiguration.RM_ADMIN_ADDRESS));

    client = new ResourceManagerAdministrationProtocolPBClientImpl(1L,
            getProtocolAddress(configuration), configuration);

  }

};