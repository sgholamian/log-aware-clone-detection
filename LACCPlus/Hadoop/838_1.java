//,temp,TestDSFailedAppMaster.java,52,80,temp,Client.java,200,226
//,3
public class xxx {
  public static void main(String[] args) {
    boolean result = false;
    try {
      TestDSFailedAppMaster appMaster = new TestDSFailedAppMaster();
      boolean doRun = appMaster.init(args);
      if (!doRun) {
        System.exit(0);
      }
      appMaster.run();
      if (appMaster.appAttemptID.getAttemptId() == 1) {
        try {
          // sleep some time, wait for the AM to launch a container.
          Thread.sleep(3000);
        } catch (InterruptedException e) {}
        // fail the first am.
        System.exit(100);
      }
      result = appMaster.finish();
    } catch (Throwable t) {
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