//,temp,LoadTestTool.java,928,939,temp,ServerCommandLine.java,147,157
//,3
public class xxx {
    @Override
    public void run() {
      try {
        int ret = ToolRunner.run(HBaseConfiguration.create(), new LoadTestTool(), workerArgs);
        if (ret != 0) {
          throw new RuntimeException("LoadTestTool exit with non-zero return code.");
        }
      } catch (Exception ex) {
        LOG.error("Error in worker thread", ex);
        workerThreadError(ex);
      }
    }

};