//,temp,HddsDatanodeService.java,233,254,temp,StorageContainerManager.java,308,331
//,3
public class xxx {
  public static void main(String[] argv) throws IOException {
    if (DFSUtil.parseHelpArgument(argv, USAGE, System.out, true)) {
      System.exit(0);
    }
    try {
      OzoneConfiguration conf = new OzoneConfiguration();
      GenericOptionsParser hParser = new GenericOptionsParser(conf, argv);
      if (!hParser.isParseSuccessful()) {
        System.err.println("USAGE: " + USAGE + "\n");
        hParser.printGenericCommandUsage(System.err);
        System.exit(1);
      }
      StringUtils.startupShutdownMessage(StorageContainerManager.class, argv,
          LOG);
      StorageContainerManager scm = createSCM(hParser.getRemainingArgs(), conf);
      if (scm != null) {
        scm.start();
        scm.join();
      }
    } catch (Throwable t) {
      LOG.error("Failed to start the StorageContainerManager.", t);
      terminate(1, t);
    }
  }

};