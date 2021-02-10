//,temp,DiskBalancerCLI.java,169,181,temp,JournalNode.java,412,420
//,3
public class xxx {
  public static void main(String[] args) throws Exception {
    StringUtils.startupShutdownMessage(JournalNode.class, args, LOG);
    try {
      System.exit(ToolRunner.run(new JournalNode(), args));
    } catch (Throwable e) {
      LOG.error("Failed to start journalnode.", e);
      terminate(-1, e);
    }
  }

};