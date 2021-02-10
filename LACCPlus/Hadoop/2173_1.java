//,temp,UnmanagedAMLauncher.java,104,117,temp,Balancer.java,922,933
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
      LOG.error("Error running Client", t);
      System.exit(1);
    }
  }

};