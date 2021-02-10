//,temp,UnmanagedAMLauncher.java,104,117,temp,Balancer.java,922,933
//,3
public class xxx {
  public static void main(String[] args) {
    if (DFSUtil.parseHelpArgument(args, USAGE, System.out, true)) {
      System.exit(0);
    }

    try {
      System.exit(ToolRunner.run(new HdfsConfiguration(), new Cli(), args));
    } catch (Throwable e) {
      LOG.error("Exiting balancer due an exception", e);
      System.exit(-1);
    }
  }

};