//,temp,AcidGuaranteesTestTool.java,405,416,temp,CreateRandomStoreFile.java,310,320
//,3
public class xxx {
  public static void main(String[] args) {
    Configuration c = HBaseConfiguration.create();
    int status;
    try {
      AcidGuaranteesTestTool test = new AcidGuaranteesTestTool();
      status = ToolRunner.run(c, test, args);
    } catch (Exception e) {
      LOG.error("Exiting due to error", e);
      status = -1;
    }
    System.exit(status);
  }

};