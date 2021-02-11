//,temp,ServerCommandLine.java,147,157,temp,PreUpgradeValidator.java,111,124
//,3
public class xxx {
  public static void main(String[] args) {
    int ret;

    Configuration conf = HBaseConfiguration.create();

    try {
      ret = ToolRunner.run(conf, new PreUpgradeValidator(), args);
    } catch (Exception e) {
      LOG.error("Error running command-line tool", e);
      ret = AbstractHBaseTool.EXIT_FAILURE;
    }

    System.exit(ret);
  }

};