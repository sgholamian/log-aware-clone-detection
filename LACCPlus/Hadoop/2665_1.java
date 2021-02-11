//,temp,ITestS3AInputStreamPerformance.java,200,205,temp,ITestAzureHugeFiles.java,138,143
//,3
public class xxx {
  protected void logTimePerIOP(String operation,
      NanoTimer timer,
      long count) {
    LOG.info("Time per {}: {} nS",
        operation, toHuman(timer.duration() / count));
  }

};