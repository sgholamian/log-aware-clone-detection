//,temp,DiskBalancerCLI.java,169,181,temp,JournalNode.java,412,420
//,3
public class xxx {
  public static void main(String[] argv) throws Exception {
    DiskBalancerCLI shell = new DiskBalancerCLI(new HdfsConfiguration());
    int res = 0;
    try {
      res = ToolRunner.run(shell, argv);
    } catch (Exception ex) {
      String msg = String.format("Exception thrown while running %s.",
          DiskBalancerCLI.class.getSimpleName());
      LOG.error(msg, ex);
      res = 1;
    }
    System.exit(res);
  }

};