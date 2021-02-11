//,temp,ServerCommandLine.java,147,157,temp,AbstractHBaseTool.java,277,286
//,3
public class xxx {
  public void doMain(String args[]) {
    try {
      int ret = ToolRunner.run(HBaseConfiguration.create(), this, args);
      if (ret != 0) {
        System.exit(ret);
      }
    } catch (Exception e) {
      LOG.error("Failed to run", e);
      System.exit(-1);
    }
  }

};