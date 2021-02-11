//,temp,Mover.java,771,783,temp,DFSZKFailoverController.java,177,197
//,3
public class xxx {
  public static void main(String[] args) {
    if (DFSUtil.parseHelpArgument(args, Cli.USAGE, System.out, true)) {
      System.exit(0);
    }

    try {
      System.exit(ToolRunner.run(new HdfsConfiguration(), new Cli(), args));
    } catch (Throwable e) {
      LOG.error("Exiting " + Mover.class.getSimpleName()
          + " due to an exception", e);
      System.exit(-1);
    }
  }

};