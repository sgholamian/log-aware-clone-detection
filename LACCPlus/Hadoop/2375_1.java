//,temp,TestDSSleepingAppMaster.java,31,59,temp,Client.java,250,276
//,3
public class xxx {
  public static void main(String[] args) {
    boolean result = false;
    try {
      TestDSSleepingAppMaster appMaster = new TestDSSleepingAppMaster();
      boolean doRun = appMaster.init(args);
      if (!doRun) {
        System.exit(0);
      }
      appMaster.run();
      if (appMaster.appAttemptID.getAttemptId() <= 2) {
        try {
          // sleep some time
          Thread.sleep(SLEEP_TIME);
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