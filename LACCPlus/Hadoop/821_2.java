//,temp,Mover.java,771,783,temp,DFSZKFailoverController.java,177,197
//,3
public class xxx {
  public static void main(String args[])
      throws Exception {
    StringUtils.startupShutdownMessage(DFSZKFailoverController.class,
        args, LOG);
    if (DFSUtil.parseHelpArgument(args, 
        ZKFailoverController.USAGE, System.out, true)) {
      System.exit(0);
    }
    
    GenericOptionsParser parser = new GenericOptionsParser(
        new HdfsConfiguration(), args);
    DFSZKFailoverController zkfc = DFSZKFailoverController.create(
        parser.getConfiguration());
    int retCode = 0;
    try {
      retCode = zkfc.run(parser.getRemainingArgs());
    } catch (Throwable t) {
      LOG.fatal("Got a fatal error, exiting now", t);
    }
    System.exit(retCode);
  }

};