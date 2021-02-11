//,temp,TestDSFailedAppMaster.java,53,81,temp,Client.java,250,276
//,3
public class xxx {
  public static void main(String[] args) {
    boolean result = false;
    try {
      Client client = new Client();
      LOG.info("Initializing Client");
      try {
        boolean doRun = client.init(args);
        if (!doRun) {
          System.exit(0);
        }
      } catch (IllegalArgumentException e) {
        System.err.println(e.getLocalizedMessage());
        client.printUsage();
        System.exit(-1);
      }
      result = client.run();
    } catch (Throwable t) {
      LOG.error("Error running Client", t);
      System.exit(1);
    }
    if (result) {
      LOG.info("Application completed successfully");
      System.exit(0);			
    } 
    LOG.error("Application failed to complete successfully");
    System.exit(2);
  }

};