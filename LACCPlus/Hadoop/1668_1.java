//,temp,UnmanagedAMLauncher.java,103,116,temp,ContainerLaunchFailAppMaster.java,59,82
//,3
public class xxx {
  public static void main(String[] args) {
    try {
      UnmanagedAMLauncher client = new UnmanagedAMLauncher();
      LOG.info("Initializing Client");
      boolean doRun = client.init(args);
      if (!doRun) {
        System.exit(0);
      }
      client.run();
    } catch (Throwable t) {
      LOG.fatal("Error running Client", t);
      System.exit(1);
    }
  }

};