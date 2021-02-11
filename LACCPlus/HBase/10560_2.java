//,temp,ServerCommandLine.java,147,157,temp,AbstractHBaseTool.java,277,286
//,3
public class xxx {
  protected void doStaticMain(String args[]) {
    int ret;
    try {
      ret = ToolRunner.run(HBaseConfiguration.create(), this, args);
    } catch (Exception ex) {
      LOG.error("Error running command-line tool", ex);
      ret = EXIT_FAILURE;
    }
    System.exit(ret);
  }

};