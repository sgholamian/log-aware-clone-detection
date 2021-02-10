//,temp,OzoneManager.java,302,324,temp,StorageContainerManager.java,308,331
//,2
public class xxx {
  public static void main(String[] argv) throws IOException {
    if (DFSUtil.parseHelpArgument(argv, USAGE, System.out, true)) {
      System.exit(0);
    }
    try {
      OzoneConfiguration conf = new OzoneConfiguration();
      GenericOptionsParser hParser = new GenericOptionsParser(conf, argv);
      if (!hParser.isParseSuccessful()) {
        System.err.println("USAGE: " + USAGE + " \n");
        hParser.printGenericCommandUsage(System.err);
        System.exit(1);
      }
      StringUtils.startupShutdownMessage(OzoneManager.class, argv, LOG);
      OzoneManager om = createOm(hParser.getRemainingArgs(), conf);
      if (om != null) {
        om.start();
        om.join();
      }
    } catch (Throwable t) {
      LOG.error("Failed to start the OzoneManager.", t);
      terminate(1, t);
    }
  }

};