//,temp,ApplicationMaster.java,293,316,temp,ContainerLaunchFailAppMaster.java,59,82
//,3
public class xxx {
  public static void main(String[] args) {
    boolean result = false;
    try {
      ContainerLaunchFailAppMaster appMaster =
        new ContainerLaunchFailAppMaster();
      LOG.info("Initializing ApplicationMaster");
      boolean doRun = appMaster.init(args);
      if (!doRun) {
        System.exit(0);
      }
      appMaster.run();
      result = appMaster.finish();
    } catch (Throwable t) {
      LOG.fatal("Error running ApplicationMaster", t);
      System.exit(1);
    }
    if (result) {
      LOG.info("Application Master completed successfully. exiting");
      System.exit(0);
    } else {
      LOG.info("Application Master failed. exiting");
      System.exit(2);
    }
  }

};