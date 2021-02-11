//,temp,HddsDatanodeService.java,233,254,temp,StorageContainerManager.java,308,331
//,3
public class xxx {
  public static void main(String[] args) {
    try {
      if (DFSUtil.parseHelpArgument(args, "Starts HDDS Datanode", System.out, false)) {
        System.exit(0);
      }
      Configuration conf = new OzoneConfiguration();
      GenericOptionsParser hParser = new GenericOptionsParser(conf, args);
      if (!hParser.isParseSuccessful()) {
        GenericOptionsParser.printGenericCommandUsage(System.err);
        System.exit(1);
      }
      StringUtils.startupShutdownMessage(HddsDatanodeService.class, args, LOG);
      DefaultMetricsSystem.initialize("HddsDatanode");
      HddsDatanodeService hddsDatanodeService =
          createHddsDatanodeService(conf);
      hddsDatanodeService.start(null);
      hddsDatanodeService.join();
    } catch (Throwable e) {
      LOG.error("Exception in HddsDatanodeService.", e);
      terminate(1, e);
    }
  }

};